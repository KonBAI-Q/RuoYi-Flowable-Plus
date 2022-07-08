package com.ruoyi.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author KonBAI
 * @createTime 2022/6/21 9:16
 */
@Data
@ApiModel("流程模型对象")
public class WfModelBo {

    @ApiModelProperty(value = "模型主键")
    private String modelId;

    @ApiModelProperty(value = "模型名称", required = true)
    @NotNull(message = "模型名称不能为空")
    private String modelName;

    @ApiModelProperty(value = "模型Key", required = true)
    @NotNull(message = "模型Key不能为空")
    private String modelKey;

    @ApiModelProperty(value = "流程分类", required = true)
    @NotBlank(message = "流程分类不能为空")
    private String category;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "表单类型", required = true)
    @NotBlank(message = "表单类型不能为空")
    private Integer formType;

    @ApiModelProperty(value = "表单主键", required = true)
    @NotBlank(message = "表单不能为空")
    private Long formId;

    @ApiModelProperty(value = "流程xml", required = true)
    @NotBlank(message = "流程xml不能为空")
    private String bpmnXml;

    @ApiModelProperty(value = "是否保存为新版本", required = true)
    private Boolean newVersion;
}
