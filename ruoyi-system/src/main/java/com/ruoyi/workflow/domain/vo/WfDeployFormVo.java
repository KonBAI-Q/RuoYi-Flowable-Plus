package com.ruoyi.workflow.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author KonBAI
 * @createTime 2022/7/17 18:29
 */
@Data
@ApiModel("部署实例和表单关联视图对象")
public class WfDeployFormVo {

    private static final long serialVersionUID = 1L;

    /**
     * 流程部署主键
     */
    @ApiModelProperty("流程部署主键")
    private String deployId;

    /**
     * 表单Key
     */
    @ApiModelProperty("表单Key")
    private String formKey;

    /**
     * 节点Key
     */
    @ApiModelProperty("节点Key")
    private String nodeKey;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;

    /**
     * 表单内容
     */
    @ApiModelProperty("表单内容")
    private String content;
}
