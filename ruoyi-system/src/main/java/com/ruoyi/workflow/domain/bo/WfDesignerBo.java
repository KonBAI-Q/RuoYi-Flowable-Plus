package com.ruoyi.workflow.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程设计业务对象
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Data
@ApiModel("流程设计业务对象")
public class WfDesignerBo {

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", required = true)
    @NotNull(message = "流程名称", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 流程分类
     */
    @ApiModelProperty(value = "流程分类", required = true)
    @NotBlank(message = "流程分类", groups = { AddGroup.class, EditGroup.class })
    private String category;

    /**
     * XML字符串
     */
    @NotBlank(message = "XML字符串", groups = { AddGroup.class, EditGroup.class })
    @ApiModelProperty(value = "XML字符串", required = true)
    private String xml;
}
