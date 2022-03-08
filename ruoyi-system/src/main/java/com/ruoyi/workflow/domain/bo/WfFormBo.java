package com.ruoyi.workflow.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程表单对象 wf_form
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("流程表单业务对象")
public class WfFormBo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @ApiModelProperty(value = "表单ID", required = true)
    @NotNull(message = "表单ID不能为空", groups = { EditGroup.class })
    private Long formId;

    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称", required = true)
    @NotBlank(message = "表单名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String formName;

    /**
     * 表单内容
     */
    @ApiModelProperty(value = "表单内容", required = true)
    @NotBlank(message = "表单内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = true)
    private String remark;
}
