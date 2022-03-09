package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
public interface IWfDefinitionService {

    boolean exist(String processDefinitionKey);


    /**
     * 流程定义列表
     *
     * @param pageQuery 分页参数
     * @return 流程定义分页列表数据
     */
    TableDataInfo<WfDefinitionVo> list(PageQuery pageQuery);

    /**
     *
     * @param processKey
     * @return
     */
    TableDataInfo<WfDefinitionVo> publishList(String processKey, PageQuery pageQuery);

    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    void importFile(String name, String category, InputStream in);

    /**
     * 读取xml
     * @param definitionId 流程定义ID
     * @return
     */
    String readXml(String definitionId) throws IOException;

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId
     * @param variables
     * @return
     */

    void startProcessInstanceById(String procDefId, Map<String, Object> variables);


    /**
     * 激活或挂起流程定义
     *
     * @param suspended    状态
     * @param definitionId 流程定义ID
     */
    void updateState(Boolean suspended, String definitionId);


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    void delete(String deployId);


    /**
     * 读取图片文件
     * @param definitionId 流程定义ID
     * @return
     */
    InputStream readImage(String definitionId);
}
