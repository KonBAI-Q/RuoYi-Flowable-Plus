package org.dromara.workflow.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.util.Date;



/**
 * 流程定义视图对象 workflow_definition
 *
 * @author KonBAI
 * @date 2022-01-17
 */
@Data
public class WfDefinitionVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    private String definitionId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程Key
     */
    private String processKey;

    /**
     * 分类编码
     */
    private String category;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 流程是否暂停（true:挂起 false:激活 ）
     */
    private Boolean suspended;

    /**
     * 部署时间
     */
    private Date deploymentTime;
}
