package com.ruoyi.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.common.enums.FlowComment;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.system.domain.SysForm;
import com.ruoyi.workflow.domain.vo.FlowDefinitionVo;
import com.ruoyi.workflow.service.IFlowDefinitionService;
import com.ruoyi.workflow.service.ISysDeployFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程定义
 *
 * @author KonBAI
 * @date 2022-01-17
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements IFlowDefinitionService {

    private final ISysDeployFormService sysDeployFormService;

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0;
    }


    /**
     * 流程定义列表
     *
     * @param pageQuery 分页参数
     * @return 流程定义分页列表数据
     */
    @Override
    public TableDataInfo<FlowDefinitionVo> list(PageQuery pageQuery) {
        Page<FlowDefinitionVo> page = new Page<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .latestVersion()
            .orderByProcessDefinitionKey().asc();
        long pageTotal = processDefinitionQuery.count();
        if (pageTotal <= 0) {
            return TableDataInfo.build();
        }
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<ProcessDefinition> definitionList = processDefinitionQuery.listPage(offset, pageQuery.getPageSize());

        List<FlowDefinitionVo> definitionVoList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            FlowDefinitionVo vo = new FlowDefinitionVo();
            vo.setDefinitionId(processDefinition.getId());
            vo.setProcessKey(processDefinition.getKey());
            vo.setProcessName(processDefinition.getName());
            vo.setVersion(processDefinition.getVersion());
            vo.setCategoryCode(processDefinition.getCategory());
            vo.setDeploymentId(processDefinition.getDeploymentId());
            vo.setSuspended(processDefinition.isSuspended());
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                vo.setFormId(sysForm.getFormId());
                vo.setFormName(sysForm.getFormName());
            }
            // 流程定义时间
            vo.setCategoryCode(deployment.getCategory());
            vo.setDeploymentTime(deployment.getDeploymentTime());
            definitionVoList.add(vo);
        }
        page.setRecords(definitionVoList);
        page.setTotal(pageTotal);
        return TableDataInfo.build(page);
    }

    @Override
    public TableDataInfo<FlowDefinitionVo> publishList(String processKey, PageQuery pageQuery) {
        Page<FlowDefinitionVo> page = new Page<>();
        // 创建查询条件
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey(processKey)
            .orderByProcessDefinitionVersion().asc();
        long pageTotal = processDefinitionQuery.count();
        if (pageTotal <= 0) {
            return TableDataInfo.build();
        }
        // 根据查询条件，查询所有版本
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
            .listPage(offset, pageQuery.getPageSize());
        List<FlowDefinitionVo> flowDefinitionVoList = processDefinitionList.stream().map(item -> {
            FlowDefinitionVo vo = new FlowDefinitionVo();
            vo.setDefinitionId(item.getId());
            vo.setProcessKey(item.getKey());
            vo.setProcessName(item.getName());
            vo.setVersion(item.getVersion());
            vo.setCategoryCode(item.getCategory());
            vo.setDeploymentId(item.getDeploymentId());
            vo.setSuspended(item.isSuspended());
            // BeanUtil.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        page.setRecords(flowDefinitionVoList);
        page.setTotal(pageTotal);
        return TableDataInfo.build(page);
    }


    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importFile(String name, String category, InputStream in) {
        String processName = name + BPMN_FILE_SUFFIX;
        // 创建流程部署
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
            .name(processName)
            .key(name)
            .category(category)
            .addInputStream(processName, in);
        // 部署
        deploymentBuilder.deploy();
    }

    /**
     * 读取xml
     *
     * @param definitionId 流程定义ID
     * @return
     */
    @Override
    public String readXml(String definitionId) throws IOException {
        InputStream inputStream = repositoryService.getProcessModel(definitionId);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    }

    /**
     * 读取xml
     *
     * @param definitionId 流程定义ID
     * @return
     */
    @Override
    public InputStream readImage(String definitionId) {
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
        //输出为图片
        return diagramGenerator.generateDiagram(
            bpmnModel,
            "png",
            Collections.emptyList(),
            Collections.emptyList(),
            "宋体",
            "宋体",
            "宋体",
            null,
            1.0,
            false);

    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程定义Id
     * @param variables 流程变量
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startProcessInstanceById(String procDefId, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(procDefId).singleResult();
            if (Objects.nonNull(processDefinition) && processDefinition.isSuspended()) {
                throw new ServiceException("流程已被挂起，请先激活流程");
            }
//           variables.put("skip", true);
//           variables.put(ProcessConstants.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);
            // 设置流程发起人Id到流程中
            String userIdStr = LoginHelper.getUserId().toString();
            identityService.setAuthenticatedUserId(userIdStr);
            variables.put(ProcessConstants.PROCESS_INITIATOR, userIdStr);
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);
            // 给第一步申请人节点设置任务执行人和意见 todo:第一个节点不设置为申请人节点有点问题？
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            if (Objects.nonNull(task)) {
                if (!StrUtil.equalsAny(task.getAssignee(), userIdStr)) {
                    throw new ServiceException("数据验证失败，该工作流第一个用户任务的指派人并非当前用户，不能执行该操作！");
                }
                taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), LoginHelper.getNickName() + "发起流程申请");
                // taskService.setAssignee(task.getId(), userIdStr);
                taskService.complete(task.getId(), variables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("流程启动错误");
        }
    }


    /**
     * 激活或挂起流程定义
     *
     * @param suspended 是否暂停状态
     * @param definitionId 流程定义ID
     */
    @Override
    public void updateState(Boolean suspended, String definitionId) {
        if (!suspended) {
            // 激活
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
        } else {
            // 挂起
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        }
    }


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }


}
