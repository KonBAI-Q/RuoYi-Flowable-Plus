package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.domain.vo.WfViewerVo;
import org.flowable.bpmn.model.UserTask;
import org.flowable.task.api.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
public interface IWfTaskService {

    /**
     * 审批任务
     *
     * @param task 请求实体参数
     */
    void complete(WfTaskBo task);

    /**
     * 驳回任务
     *
     * @param bo
     */
    void taskReject(WfTaskBo bo);


    /**
     * 退回任务
     *
     * @param bo 请求实体参数
     */
    void taskReturn(WfTaskBo bo);

    /**
     * 获取所有可回退的节点
     *
     * @param bo
     * @return
     */
    List<UserTask> findReturnTaskList(WfTaskBo bo);

    /**
     * 删除任务
     *
     * @param bo 请求实体参数
     */
    void deleteTask(WfTaskBo bo);

    /**
     * 认领/签收任务
     *
     * @param bo 请求实体参数
     */
    void claim(WfTaskBo bo);

    /**
     * 取消认领/签收任务
     *
     * @param bo 请求实体参数
     */
    void unClaim(WfTaskBo bo);

    /**
     * 委派任务
     *
     * @param bo 请求实体参数
     */
    void delegateTask(WfTaskBo bo);


    /**
     * 转办任务
     *
     * @param bo 请求实体参数
     */
    void assignTask(WfTaskBo bo);

    /**
     * 我发起的流程
     *
     * @return
     */
    TableDataInfo<WfTaskVo> myProcess(PageQuery pageQuery);

    /**
     * 取消申请
     * @param bo
     * @return
     */
    void stopProcess(WfTaskBo bo);

    /**
     * 撤回流程
     * @param bo
     * @return
     */
    void revokeProcess(WfTaskBo bo);


    /**
     * 代办任务列表
     *
     * @return
     */
    TableDataInfo<WfTaskVo> todoList(PageQuery pageQuery);


    /**
     * 已办任务列表
     *
     * @return
     */
    TableDataInfo<WfTaskVo> finishedList(PageQuery pageQuery);

    /**
     * 流程历史流转记录
     *
     * @param procInsId 流程实例Id
     * @return
     */
    Map<String, Object> flowRecord(String procInsId, String deployId);

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     * @return
     */
    Task getTaskForm(String taskId);

    /**
     * 获取流程过程图
     * @param processId
     * @return
     */
    InputStream diagram(String processId);

    /**
     * 获取流程执行过程
     * @param procInsId
     * @return
     */
    WfViewerVo getFlowViewer(String procInsId);

    /**
     * 获取流程变量
     * @param taskId
     * @return
     */
    R processVariables(String taskId);

    /**
     * 获取下一节点
     * @param bo 任务
     * @return
     */
    R getNextFlowNode(WfTaskBo bo);
}