package com.ruoyi.workflow.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.common.enums.FlowComment;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.flowable.flow.CustomProcessDiagramGenerator;
import com.ruoyi.flowable.flow.FindNextNodeUtil;
import com.ruoyi.flowable.flow.FlowableUtils;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.domain.dto.WfCommentDto;
import com.ruoyi.workflow.domain.dto.WfNextDto;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.domain.vo.WfViewerVo;
import com.ruoyi.workflow.service.IWfDeployFormService;
import com.ruoyi.workflow.service.IWfTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class WfTaskServiceImpl extends FlowServiceFactory implements IWfTaskService {

    private final ISysUserService sysUserService;

    private final ISysRoleService sysRoleService;

    private final IWfDeployFormService deployFormService;

    /**
     * 完成任务
     *
     * @param taskBo 请求实体参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void complete(WfTaskBo taskBo) {
        Task task = taskService.createTaskQuery().taskId(taskBo.getTaskId()).singleResult();
        if (Objects.isNull(task)) {
            throw new ServiceException("任务不存在");
        }
        if (DelegationState.PENDING.equals(task.getDelegationState())) {
            taskService.addComment(taskBo.getTaskId(), taskBo.getInstanceId(), FlowComment.DELEGATE.getType(), taskBo.getComment());
            taskService.resolveTask(taskBo.getTaskId(), taskBo.getValues());
        } else {
            taskService.addComment(taskBo.getTaskId(), taskBo.getInstanceId(), FlowComment.NORMAL.getType(), taskBo.getComment());
            Long userId = LoginHelper.getUserId();
            taskService.setAssignee(taskBo.getTaskId(), userId.toString());
            if (ObjectUtil.isNotEmpty(taskBo.getValues())) {
                taskService.complete(taskBo.getTaskId(), taskBo.getValues());
            } else {
                taskService.complete(taskBo.getTaskId());
            }
        }
    }

    /**
     * 驳回任务
     *
     * @param bo
     */
    @Override
    public void taskReject(WfTaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("获取任务信息异常！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        if (allElements != null) {
            for (FlowElement flowElement : allElements) {
                // 类型为用户节点
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    // 获取节点信息
                    source = flowElement;
                }
            }
        }

        // 目的获取所有跳转到的节点 targetIds
        // 获取当前节点的所有父级用户任务节点
        // 深度优先算法思想：延边迭代深入
        List<UserTask> parentUserTaskList = FlowableUtils.iteratorFindParentUserTasks(source, null, null);
        if (parentUserTaskList == null || parentUserTaskList.size() == 0) {
            throw new RuntimeException("当前节点为初始任务节点，不能驳回");
        }
        // 获取活动 ID 即节点 Key
        List<String> parentUserTaskKeyList = new ArrayList<>();
        parentUserTaskList.forEach(item -> parentUserTaskKeyList.add(item.getId()));
        // 获取全部历史节点活动实例，即已经走过的节点历史，数据采用开始时间升序
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).orderByHistoricTaskInstanceStartTime().asc().list();
        // 数据清洗，将回滚导致的脏数据清洗掉
        List<String> lastHistoricTaskInstanceList = FlowableUtils.historicTaskInstanceClean(allElements, historicTaskInstanceList);
        // 此时历史任务实例为倒序，获取最后走的节点
        List<String> targetIds = new ArrayList<>();
        // 循环结束标识，遇到当前目标节点的次数
        int number = 0;
        StringBuilder parentHistoricTaskKey = new StringBuilder();
        for (String historicTaskInstanceKey : lastHistoricTaskInstanceList) {
            // 当会签时候会出现特殊的，连续都是同一个节点历史数据的情况，这种时候跳过
            if (parentHistoricTaskKey.toString().equals(historicTaskInstanceKey)) {
                continue;
            }
            parentHistoricTaskKey = new StringBuilder(historicTaskInstanceKey);
            if (historicTaskInstanceKey.equals(task.getTaskDefinitionKey())) {
                number++;
            }
            // 在数据清洗后，历史节点就是唯一一条从起始到当前节点的历史记录，理论上每个点只会出现一次
            // 在流程中如果出现循环，那么每次循环中间的点也只会出现一次，再出现就是下次循环
            // number == 1，第一次遇到当前节点
            // number == 2，第二次遇到，代表最后一次的循环范围
            if (number == 2) {
                break;
            }
            // 如果当前历史节点，属于父级的节点，说明最后一次经过了这个点，需要退回这个点
            if (parentUserTaskKeyList.contains(historicTaskInstanceKey)) {
                targetIds.add(historicTaskInstanceKey);
            }
        }


        // 目的获取所有需要被跳转的节点 currentIds
        // 取其中一个父级任务，因为后续要么存在公共网关，要么就是串行公共线路
        UserTask oneUserTask = parentUserTaskList.get(0);
        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需驳回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(oneUserTask, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));


        // 规定：并行网关之前节点必须需存在唯一用户任务节点，如果出现多个任务节点，则并行网关节点默认为结束节点，原因为不考虑多对多情况
        if (targetIds.size() > 1 && currentIds.size() > 1) {
            throw new RuntimeException("任务出现多对多情况，无法撤回");
        }

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if (currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置驳回意见
        currentTaskIds.forEach(item -> taskService.addComment(item, task.getProcessInstanceId(), FlowComment.REJECT.getType(), bo.getComment()));

        try {
            // 如果父级任务多于 1 个，说明当前节点不是并行节点，原因为不考虑多对多情况
            if (targetIds.size() > 1) {
                // 1 对 多任务跳转，currentIds 当前节点(1)，targetIds 跳转到的节点(多)
                runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(task.getProcessInstanceId()).
                    moveSingleActivityIdToActivityIds(currentIds.get(0), targetIds).changeState();
            }
            // 如果父级任务只有一个，因此当前任务可能为网关中的任务
            if (targetIds.size() == 1) {
                // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetIds.get(0) 跳转到的节点(1)
                runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(task.getProcessInstanceId())
                    .moveActivityIdsToSingleActivityId(currentIds, targetIds.get(0)).changeState();
            }
        } catch (FlowableObjectNotFoundException e) {
            throw new RuntimeException("未找到流程实例，流程可能已发生变化");
        } catch (FlowableException e) {
            throw new RuntimeException("无法取消或开始活动");
        }

    }

    /**
     * 退回任务
     *
     * @param bo 请求实体参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void taskReturn(WfTaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        if (ObjectUtil.isNull(task)) {
            throw new RuntimeException("获取任务信息异常！");
        }
        if (task.isSuspended()) {
            throw new RuntimeException("任务处于挂起状态");
        }
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        // 获取跳转的节点元素
        FlowElement target = null;
        if (allElements != null) {
            for (FlowElement flowElement : allElements) {
                // 当前任务节点元素
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    source = flowElement;
                }
                // 跳转的节点元素
                if (flowElement.getId().equals(bo.getTargetKey())) {
                    target = flowElement;
                }
            }
        }

        // 从当前节点向前扫描
        // 如果存在路线上不存在目标节点，说明目标节点是在网关上或非同一路线上，不可跳转
        // 否则目标节点相对于当前节点，属于串行
        Boolean isSequential = FlowableUtils.iteratorCheckSequentialReferTarget(source, bo.getTargetKey(), null, null);
        if (!isSequential) {
            throw new RuntimeException("当前节点相对于目标节点，不属于串行关系，无法回退");
        }


        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需退回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(target, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if (currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置回退意见
        for (String currentTaskId : currentTaskIds) {
            taskService.addComment(currentTaskId, task.getProcessInstanceId(), FlowComment.REBACK.getType(), bo.getComment());
        }

        try {
            // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetKey 跳转到的节点(1)
            runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(task.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(currentIds, bo.getTargetKey()).changeState();
        } catch (FlowableObjectNotFoundException e) {
            throw new RuntimeException("未找到流程实例，流程可能已发生变化");
        } catch (FlowableException e) {
            throw new RuntimeException("无法取消或开始活动");
        }
    }


    /**
     * 获取所有可回退的节点
     *
     * @param bo
     * @return
     */
    @Override
    public List<UserTask> findReturnTaskList(WfTaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 获取所有节点信息，暂不考虑子流程情况
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        // 获取当前任务节点元素
        UserTask source = null;
        if (flowElements != null) {
            for (FlowElement flowElement : flowElements) {
                // 类型为用户节点
                if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    source = (UserTask) flowElement;
                }
            }
        }
        // 获取节点的所有路线
        List<List<UserTask>> roads = FlowableUtils.findRoad(source, null, null, null);
        // 可回退的节点列表
        List<UserTask> userTaskList = new ArrayList<>();
        for (List<UserTask> road : roads) {
            if (userTaskList.size() == 0) {
                // 还没有可回退节点直接添加
                userTaskList = road;
            } else {
                // 如果已有回退节点，则比对取交集部分
                userTaskList.retainAll(road);
            }
        }
        return userTaskList;
    }

    /**
     * 删除任务
     *
     * @param bo 请求实体参数
     */
    @Override
    public void deleteTask(WfTaskBo bo) {
        // todo 待确认删除任务是物理删除任务 还是逻辑删除，让这个任务直接通过？
        taskService.deleteTask(bo.getTaskId(), bo.getComment());
    }

    /**
     * 认领/签收任务
     *
     * @param bo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claim(WfTaskBo bo) {
        taskService.claim(bo.getTaskId(), bo.getUserId());
    }

    /**
     * 取消认领/签收任务
     *
     * @param bo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unClaim(WfTaskBo bo) {
        taskService.unclaim(bo.getTaskId());
    }

    /**
     * 委派任务
     *
     * @param bo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(WfTaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        if (ObjectUtil.isEmpty(task)) {
            throw new ServiceException("获取任务失败！");
        }
        StringBuilder commentBuilder = new StringBuilder(LoginHelper.getNickName())
            .append("->");
        SysUser user = sysUserService.selectUserById(Long.parseLong(bo.getUserId()));
        if (ObjectUtil.isNotNull(user)) {
            commentBuilder.append(user.getNickName());
        } else {
            commentBuilder.append(bo.getUserId());
        }
        if (StringUtils.isNotBlank(bo.getComment())) {
            commentBuilder.append(": ").append(bo.getComment());
        }
        // 添加审批意见
        taskService.addComment(bo.getTaskId(), task.getProcessInstanceId(), FlowComment.DELEGATE.getType(), commentBuilder.toString());
        // 设置办理人为当前登录人
        taskService.setOwner(bo.getTaskId(), LoginHelper.getUserId().toString());
        // 执行委派
        taskService.delegateTask(bo.getTaskId(), bo.getUserId());
    }


    /**
     * 转办任务
     *
     * @param bo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferTask(WfTaskBo bo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        if (ObjectUtil.isEmpty(task)) {
            throw new ServiceException("获取任务失败！");
        }
        StringBuilder commentBuilder = new StringBuilder(LoginHelper.getNickName())
            .append("->");
        SysUser user = sysUserService.selectUserById(Long.parseLong(bo.getUserId()));
        if (ObjectUtil.isNotNull(user)) {
            commentBuilder.append(user.getNickName());
        } else {
            commentBuilder.append(bo.getUserId());
        }
        if (StringUtils.isNotBlank(bo.getComment())) {
            commentBuilder.append(": ").append(bo.getComment());
        }
        // 添加审批意见
        taskService.addComment(bo.getTaskId(), task.getProcessInstanceId(), FlowComment.TRANSFER.getType(), commentBuilder.toString());
        // 设置拥有者为当前登录人
        taskService.setOwner(bo.getTaskId(), LoginHelper.getUserId().toString());
        // 转办任务
        taskService.setAssignee(bo.getTaskId(), bo.getUserId());
    }

    /**
     * 我发起的流程
     *
     * @return
     */
    @Override
    public TableDataInfo<WfTaskVo> myProcess(PageQuery pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        Long userId = LoginHelper.getUserId();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
            .startedBy(userId.toString())
            .orderByProcessInstanceStartTime()
            .desc();
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery
            .listPage(offset, pageQuery.getPageSize());
        page.setTotal(historicProcessInstanceQuery.count());
        List<WfTaskVo> taskVoList = new ArrayList<>();
        for (HistoricProcessInstance hisIns : historicProcessInstances) {
            WfTaskVo taskVo = new WfTaskVo();
            taskVo.setCreateTime(hisIns.getStartTime());
            taskVo.setFinishTime(hisIns.getEndTime());
            taskVo.setProcInsId(hisIns.getId());

            // 计算耗时
            if (Objects.nonNull(hisIns.getEndTime())) {
                long time = hisIns.getEndTime().getTime() - hisIns.getStartTime().getTime();
                taskVo.setDuration(getDate(time));
            } else {
                long time = System.currentTimeMillis() - hisIns.getStartTime().getTime();
                taskVo.setDuration(getDate(time));
            }
            // 流程部署实例信息
            Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(hisIns.getDeploymentId()).singleResult();
            taskVo.setDeployId(hisIns.getDeploymentId());
            taskVo.setProcDefId(hisIns.getProcessDefinitionId());
            taskVo.setProcDefName(hisIns.getProcessDefinitionName());
            taskVo.setProcDefVersion(hisIns.getProcessDefinitionVersion());
            taskVo.setCategory(deployment.getCategory());
            // 当前所处流程 todo: 本地启动放开以下注释
            // List<Task> taskList = taskService.createTaskQuery().processInstanceId(hisIns.getId()).list();
            // if (CollectionUtils.isNotEmpty(taskList)) {
            //     flowTask.setTaskId(taskList.get(0).getId());
            // } else {
            //     List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(hisIns.getId()).orderByHistoricTaskInstanceEndTime().desc().list();
            //     flowTask.setTaskId(historicTaskInstance.get(0).getId());
            // }
            taskVoList.add(taskVo);
        }
        page.setRecords(taskVoList);
        return TableDataInfo.build(page);
    }

    /**
     * 取消申请
     *
     * @param bo
     * @return
     */
    @Override
    public void stopProcess(WfTaskBo bo) {
        List<Task> task = taskService.createTaskQuery().processInstanceId(bo.getInstanceId()).list();
        if (CollectionUtils.isEmpty(task)) {
            throw new RuntimeException("流程未启动或已执行完成，取消申请失败");
        }

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(bo.getInstanceId()).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if (Objects.nonNull(bpmnModel)) {
            Process process = bpmnModel.getMainProcess();
            List<EndEvent> endNodes = process.findFlowElementsOfType(EndEvent.class, false);
            if (CollectionUtils.isNotEmpty(endNodes)) {
                Authentication.setAuthenticatedUserId(LoginHelper.getUserId().toString());
//                taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.STOP.getType(),
//                        StringUtils.isBlank(flowTaskVo.getComment()) ? "取消申请" : flowTaskVo.getComment());
                String endId = endNodes.get(0).getId();
                List<Execution> executions = runtimeService.createExecutionQuery()
                    .parentId(processInstance.getProcessInstanceId()).list();
                List<String> executionIds = new ArrayList<>();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                runtimeService.createChangeActivityStateBuilder()
                    .moveExecutionsToSingleActivityId(executionIds, endId).changeState();
            }
        }
    }

    /**
     * 撤回流程  目前存在错误
     *
     * @param bo
     * @return
     */
    @Override
    public void revokeProcess(WfTaskBo bo) {
        Task task = taskService.createTaskQuery().processInstanceId(bo.getInstanceId()).singleResult();
        if (task == null) {
            throw new RuntimeException("流程未启动或已执行完成，无法撤回");
        }

        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
            .processInstanceId(task.getProcessInstanceId())
            .orderByTaskCreateTime()
            .asc()
            .list();
        String myTaskId = null;
        HistoricTaskInstance myTask = null;
        for (HistoricTaskInstance hti : htiList) {
            if (LoginHelper.getUserId().toString().equals(hti.getAssignee())) {
                myTaskId = hti.getId();
                myTask = hti;
                break;
            }
        }
        if (null == myTaskId) {
            throw new RuntimeException("该任务非当前用户提交，无法撤回");
        }

        String processDefinitionId = myTask.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        //变量
//      Map<String, VariableInstance> variables = runtimeService.getVariableInstances(currentTask.getExecutionId());
        String myActivityId = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
            .executionId(myTask.getExecutionId()).finished().list();
        for (HistoricActivityInstance hai : haiList) {
            if (myTaskId.equals(hai.getTaskId())) {
                myActivityId = hai.getActivityId();
                break;
            }
        }
        FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);

        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);

        //记录原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<>(flowNode.getOutgoingFlows());
    }

    /**
     * 代办任务列表
     *
     * @return
     */
    @Override
    public TableDataInfo<WfTaskVo> todoList(PageQuery pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        Long userId = LoginHelper.getUserId();
        TaskQuery taskQuery = taskService.createTaskQuery()
            .active()
            .includeProcessVariables()
            .taskCandidateOrAssigned(userId.toString())
            .orderByTaskCreateTime().desc();
        page.setTotal(taskQuery.count());
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<Task> taskList = taskQuery.listPage(offset, pageQuery.getPageSize());
        List<WfTaskVo> flowList = new ArrayList<>();
        for (Task task : taskList) {
            WfTaskVo flowTask = new WfTaskVo();
            // 当前流程信息
            flowTask.setTaskId(task.getId());
            flowTask.setTaskDefKey(task.getTaskDefinitionKey());
            flowTask.setCreateTime(task.getCreateTime());
            flowTask.setProcDefId(task.getProcessDefinitionId());
            flowTask.setTaskName(task.getName());
            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId())
                .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(task.getProcessInstanceId());

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
            SysUser startUser = sysUserService.selectUserById(Long.parseLong(historicProcessInstance.getStartUserId()));
//            SysUser startUser = sysUserService.selectUserById(Long.parseLong(task.getAssignee()));
            flowTask.setStartUserId(startUser.getNickName());
            flowTask.setStartUserName(startUser.getNickName());
            flowTask.setStartDeptName(startUser.getDept().getDeptName());

            // 流程变量
            flowTask.setProcVars(this.getProcessVariables(task.getId()));

            flowList.add(flowTask);
        }

        page.setRecords(flowList);
        return TableDataInfo.build(page);
    }


    /**
     * 已办任务列表
     *
     * @return
     */
    @Override
    public TableDataInfo<WfTaskVo> finishedList(PageQuery pageQuery) {
        Page<WfTaskVo> page = new Page<>();
        Long userId = LoginHelper.getUserId();
        HistoricTaskInstanceQuery taskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
            .includeProcessVariables()
            .finished()
            .taskAssignee(userId.toString())
            .orderByHistoricTaskInstanceEndTime()
            .desc();
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<HistoricTaskInstance> historicTaskInstanceList = taskInstanceQuery.listPage(offset, pageQuery.getPageSize());
        List<WfTaskVo> hisTaskList = Lists.newArrayList();
        for (HistoricTaskInstance histTask : historicTaskInstanceList) {
            WfTaskVo flowTask = new WfTaskVo();
            // 当前流程信息
            flowTask.setTaskId(histTask.getId());
            // 审批人员信息
            flowTask.setCreateTime(histTask.getCreateTime());
            flowTask.setFinishTime(histTask.getEndTime());
            flowTask.setDuration(getDate(histTask.getDurationInMillis()));
            flowTask.setProcDefId(histTask.getProcessDefinitionId());
            flowTask.setTaskDefKey(histTask.getTaskDefinitionKey());
            flowTask.setTaskName(histTask.getName());

            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(histTask.getProcessDefinitionId())
                .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(histTask.getProcessInstanceId());
            flowTask.setHisProcInsId(histTask.getProcessInstanceId());

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(histTask.getProcessInstanceId())
                .singleResult();
            SysUser startUser = sysUserService.selectUserById(Long.parseLong(historicProcessInstance.getStartUserId()));
            flowTask.setStartUserId(startUser.getNickName());
            flowTask.setStartUserName(startUser.getNickName());
            flowTask.setStartDeptName(startUser.getDept().getDeptName());

            // 流程变量
            flowTask.setProcVars(this.getProcessVariables(histTask.getId()));

            hisTaskList.add(flowTask);
        }
        page.setTotal(taskInstanceQuery.count());
        page.setRecords(hisTaskList);
//        Map<String, Object> result = new HashMap<>();
//        result.put("result",page);
//        result.put("finished",true);
        return TableDataInfo.build(page);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * 流程历史流转记录
     *
     * @param procInsId 流程实例Id
     * @return
     */
    @Override
    public Map<String, Object> flowRecord(String procInsId, String deployId) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(procInsId)) {
            List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(procInsId)
                .orderByHistoricActivityInstanceStartTime()
                .desc().list();
            List<WfTaskVo> hisFlowList = new ArrayList<>();
            for (HistoricActivityInstance histIns : list) {
                if (StringUtils.isNotBlank(histIns.getTaskId())) {
                    WfTaskVo flowTask = new WfTaskVo();
                    flowTask.setProcDefId(histIns.getProcessDefinitionId());
                    flowTask.setTaskId(histIns.getTaskId());
                    flowTask.setTaskName(histIns.getActivityName());
                    flowTask.setCreateTime(histIns.getStartTime());
                    flowTask.setFinishTime(histIns.getEndTime());
                    if (StringUtils.isNotBlank(histIns.getAssignee())) {
                        SysUser sysUser = sysUserService.selectUserById(Long.parseLong(histIns.getAssignee()));
                        flowTask.setAssigneeId(sysUser.getUserId());
                        flowTask.setAssigneeName(sysUser.getNickName());
                        flowTask.setDeptName(sysUser.getDept().getDeptName());
                    }
                    // 展示审批人员
                    List<HistoricIdentityLink> linksForTask = historyService.getHistoricIdentityLinksForTask(histIns.getTaskId());
                    StringBuilder stringBuilder = new StringBuilder();
                    for (HistoricIdentityLink identityLink : linksForTask) {
                        if ("candidate".equals(identityLink.getType())) {
                            if (StringUtils.isNotBlank(identityLink.getUserId())) {
                                SysUser sysUser = sysUserService.selectUserById(Long.parseLong(identityLink.getUserId()));
                                stringBuilder.append(sysUser.getNickName()).append(",");
                            }
                            if (StringUtils.isNotBlank(identityLink.getGroupId())) {
                                SysRole sysRole = sysRoleService.selectRoleById(Long.parseLong(identityLink.getGroupId()));
                                stringBuilder.append(sysRole.getRoleName()).append(",");
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(stringBuilder)) {
                        flowTask.setCandidate(stringBuilder.substring(0, stringBuilder.length() - 1));
                    }

                    flowTask.setDuration(histIns.getDurationInMillis() == null || histIns.getDurationInMillis() == 0 ? null : getDate(histIns.getDurationInMillis()));
                    // 获取意见评论内容
                    List<Comment> commentList = taskService.getProcessInstanceComments(histIns.getProcessInstanceId());
                    commentList.forEach(comment -> {
                        if (histIns.getTaskId().equals(comment.getTaskId())) {
                            flowTask.setComment(WfCommentDto.builder().type(comment.getType()).comment(comment.getFullMessage()).build());
                        }
                    });
                    hisFlowList.add(flowTask);
                }
            }
            map.put("flowList", hisFlowList);
//            // 查询当前任务是否完成
//            List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInsId).list();
//            if (CollectionUtils.isNotEmpty(taskList)) {
//                map.put("finished", true);
//            } else {
//                map.put("finished", false);
//            }
        }
        // 第一次申请获取初始化表单
        if (StringUtils.isNotBlank(deployId)) {
            WfFormVo formVo = deployFormService.selectDeployFormByDeployId(deployId);
            if (Objects.isNull(formVo)) {
                throw new ServiceException("请先配置流程表单");
            }
            map.put("formData", JsonUtils.parseObject(formVo.getContent(), Map.class));
        }
        return map;
    }

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     * @return
     */
    @Override
    public Task getTaskForm(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 获取流程过程图
     *
     * @param processId
     * @return
     */
    @Override
    public InputStream diagram(String processId) {
        String processDefinitionId;
        // 获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 如果流程已经结束，则得到结束节点
        if (Objects.isNull(processInstance)) {
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedFlowList = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

        List<String> highLightedFlows = new ArrayList<>();
        List<String> highLightedNodes = new ArrayList<>();
        //高亮线
        for (HistoricActivityInstance tempActivity : highLightedFlowList) {
            if ("sequenceFlow".equals(tempActivity.getActivityType())) {
                //高亮线
                highLightedFlows.add(tempActivity.getActivityId());
            } else {
                //高亮节点
                highLightedNodes.add(tempActivity.getActivityId());
            }
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        //获取自定义图片生成器
        ProcessDiagramGenerator diagramGenerator = new CustomProcessDiagramGenerator();
        return diagramGenerator.generateDiagram(bpmnModel, "png", highLightedNodes, highLightedFlows, configuration.getActivityFontName(),
            configuration.getLabelFontName(), configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, true);

    }

    /**
     * 获取流程执行过程
     *
     * @param procInsId
     * @return
     */
    @Override
    public WfViewerVo getFlowViewer(String procInsId) {
        // 构建查询条件
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery()
            .processInstanceId(procInsId);
        List<HistoricActivityInstance> allActivityInstanceList = query.list();
        if (CollUtil.isEmpty(allActivityInstanceList)) {
            return new WfViewerVo();
        }
        // 获取流程发布Id信息
        String processDefinitionId = allActivityInstanceList.get(0).getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        if (ObjectUtil.isNull(bpmnModel)) {
            throw new ServiceException("流程模型不存在");
        }
        Map<String, List<String>> sequenceElementMap = new HashMap<>();
        Map<String, String> sequenceFlowMap = new HashMap<>();
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof SequenceFlow) {
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                String sourceRef = sequenceFlow.getSourceRef();
                String targetRef = sequenceFlow.getTargetRef();
                List<String> targetRefList = sequenceElementMap.get(sourceRef);
                if (CollUtil.isEmpty(targetRefList)) {
                    sequenceElementMap.put(sourceRef, ListUtil.toList(targetRef));
                } else {
                    targetRefList.add(targetRef);
                }
                sequenceFlowMap.put(sourceRef + targetRef, sequenceFlow.getId());
            }
        }
        // 查询所有已完成的元素
        List<HistoricActivityInstance> finishedElementList = allActivityInstanceList.stream()
            .filter(item -> ObjectUtil.isNotNull(item.getEndTime())).collect(Collectors.toList());
        // 所有已完成的连线
        Set<String> finishedSequenceFlowSet = new HashSet<>();
        // 所有已完成的任务节点
        Set<String> finishedTaskSet = new HashSet<>();
        finishedElementList.forEach(item -> {
            if ("sequenceFlow".equals(item.getActivityType())) {
                finishedSequenceFlowSet.add(item.getActivityId());
            } else {
                finishedTaskSet.add(item.getActivityId());
            }
        });
        // 查询所有未结束的节点
        Set<String> unfinishedTaskSet = allActivityInstanceList.stream()
            .filter(item -> ObjectUtil.isNull(item.getEndTime()))
            .map(HistoricActivityInstance::getActivityId)
            .collect(Collectors.toSet());

        Set<String> rejectedTaskSet = new LinkedHashSet<>();
        Set<String> sourceTaskSet = unfinishedTaskSet;
        while (CollUtil.isNotEmpty(sourceTaskSet)) {
            Set<String> nextIdSet = new HashSet<>();
            for (String previousId : sourceTaskSet) {
                List<String> nextIdList = sequenceElementMap.get(previousId);
                if (CollUtil.isEmpty(nextIdList)) {
                    continue;
                }
                nextIdList.forEach(nextId -> {
                    if (finishedTaskSet.contains(nextId)) {
                        nextIdSet.add(nextId);
                        String rejectedSequenceFlow = sequenceFlowMap.get(previousId + nextId);
                        if (finishedSequenceFlowSet.contains(rejectedSequenceFlow)) {
                            nextIdSet.add(rejectedSequenceFlow);
                        }
                    }
                });
            }
            rejectedTaskSet.addAll(nextIdSet);
            sourceTaskSet = nextIdSet;
        }
        return new WfViewerVo(finishedTaskSet, finishedSequenceFlowSet, unfinishedTaskSet, rejectedTaskSet);
    }

    /**
     * 获取流程变量
     *
     * @param taskId 任务ID
     * @return 流程变量
     */
    @Override
    public Map<String, Object> getProcessVariables(String taskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
            .includeProcessVariables()
            .finished()
            .taskId(taskId)
            .singleResult();
        if (Objects.nonNull(historicTaskInstance)) {
            return historicTaskInstance.getProcessVariables();
        }
        return taskService.getVariables(taskId);
    }

    /**
     * 获取下一节点
     *
     * @param bo 任务
     * @return
     */
    @Override
    public WfNextDto getNextFlowNode(WfTaskBo bo) {
        Task task = taskService.createTaskQuery().taskId(bo.getTaskId()).singleResult();
        WfNextDto nextDto = new WfNextDto();
        if (Objects.nonNull(task)) {
            List<UserTask> nextUserTask = FindNextNodeUtil.getNextUserTasks(repositoryService, task, new HashMap<>());
            if (CollectionUtils.isNotEmpty(nextUserTask)) {
                for (UserTask userTask : nextUserTask) {
                    MultiInstanceLoopCharacteristics multiInstance = userTask.getLoopCharacteristics();
                    // 会签节点
                    if (Objects.nonNull(multiInstance)) {
                        List<SysUser> list = sysUserService.selectUserList(new SysUser());

                        nextDto.setVars(ProcessConstants.PROCESS_MULTI_INSTANCE_USER);
                        nextDto.setType(ProcessConstants.PROCESS_MULTI_INSTANCE);
                        nextDto.setUserList(list);
                    } else {

                        // 读取自定义节点属性 判断是否是否需要动态指定任务接收人员、组
                        String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
                        String userType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_USER_TYPE);

                        if (ProcessConstants.DATA_TYPE.equals(dataType)) {
                            // 指定单个人员
                            if (ProcessConstants.USER_TYPE_ASSIGNEE.equals(userType)) {
                                List<SysUser> list = sysUserService.selectUserList(new SysUser());

                                nextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                nextDto.setType(ProcessConstants.USER_TYPE_ASSIGNEE);
                                nextDto.setUserList(list);
                            }
                            // 候选人员(多个)
                            if (ProcessConstants.USER_TYPE_USERS.equals(userType)) {
                                List<SysUser> list = sysUserService.selectUserList(new SysUser());

                                nextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                nextDto.setType(ProcessConstants.USER_TYPE_USERS);
                                nextDto.setUserList(list);
                            }
                            // 候选组
                            if (ProcessConstants.USER_TYPE_ROUPS.equals(userType)) {
                                List<SysRole> sysRoles = sysRoleService.selectRoleAll();

                                nextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                nextDto.setType(ProcessConstants.USER_TYPE_ROUPS);
                                nextDto.setRoleList(sysRoles);
                            }
                        }
                    }
                }
            } else {
                return null;
            }
        }
        return nextDto;
    }

    /**
     * 启动第一个任务
     * @param processInstance 流程实例
     * @param variables 流程参数
     */
    public void startFirstTask(ProcessInstance processInstance, Map<String, Object> variables) {
        // 给第一步申请人节点设置任务执行人和意见 todo:第一个节点不设置为申请人节点有点问题？
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        if (Objects.nonNull(task)) {
            String userIdStr = (String) variables.get(ProcessConstants.PROCESS_INITIATOR);
            if (!StrUtil.equalsAny(task.getAssignee(), userIdStr)) {
                throw new ServiceException("数据验证失败，该工作流第一个用户任务的指派人并非当前用户，不能执行该操作！");
            }
            taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), LoginHelper.getNickName() + "发起流程申请");
            // taskService.setAssignee(task.getId(), userIdStr);
            taskService.complete(task.getId(), variables);
        }
    }

    /**
     * 流程完成时间处理
     *
     * @param ms
     * @return
     */
    private String getDate(long ms) {

        long day = ms / (24 * 60 * 60 * 1000);
        long hour = (ms / (60 * 60 * 1000) - day * 24);
        long minute = ((ms / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (ms / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);

        if (day > 0) {
            return day + "天" + hour + "小时" + minute + "分钟";
        }
        if (hour > 0) {
            return hour + "小时" + minute + "分钟";
        }
        if (minute > 0) {
            return minute + "分钟";
        }
        if (second > 0) {
            return second + "秒";
        } else {
            return 0 + "秒";
        }
    }
}
