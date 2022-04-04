package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;
import com.ruoyi.workflow.domain.vo.WfTaskVo;

import java.util.Map;

/**
 * @author KonBAI
 * @createTime 2022/3/24 18:57
 */
public interface IWfProcessService {

    /**
     * 查询可发起流程列表
     * @param pageQuery 分页参数
     * @return
     */
    TableDataInfo<WfDefinitionVo> processList(PageQuery pageQuery);

    /**
     * 启动流程实例
     * @param procDefId 流程定义ID
     * @param variables 扩展参数
     */
    void startProcess(String procDefId, Map<String, Object> variables);

    /**
     * 通过DefinitionKey启动流程
     * @param procDefKey 流程定义Key
     * @param variables 扩展参数
     */
    void startProcessByDefKey(String procDefKey, Map<String, Object> variables);

    /**
     * 查询我的流程列表
     * @param pageQuery 分页参数
     */
    TableDataInfo<WfTaskVo> queryPageOwnProcessList(PageQuery pageQuery);

    /**
     * 查询代办任务列表
     * @param pageQuery 分页参数
     */
    TableDataInfo<WfTaskVo> queryPageTodoProcessList(PageQuery pageQuery);

    /**
     * 查询已办任务列表
     * @param pageQuery 分页参数
     */
    TableDataInfo<WfTaskVo> queryPageFinishedProcessList(PageQuery pageQuery);
}
