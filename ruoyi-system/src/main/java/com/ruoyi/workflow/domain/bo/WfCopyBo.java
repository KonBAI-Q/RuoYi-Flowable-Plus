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
 * 流程抄送业务对象 wf_copy
 *
 * @author ruoyi
 * @date 2022-05-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("流程抄送业务对象")
public class WfCopyBo extends BaseEntity {

    /**
     * 抄送主键
     */
    @ApiModelProperty(value = "抄送主键", required = true)
    @NotNull(message = "抄送主键不能为空", groups = { EditGroup.class })
    private Long copyId;

    /**
     * 抄送标题
     */
    @ApiModelProperty(value = "抄送标题", required = true)
    @NotNull(message = "抄送标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 流程主键
     */
    @ApiModelProperty(value = "流程主键", required = true)
    @NotBlank(message = "流程主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processId;

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", required = true)
    @NotBlank(message = "流程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processName;

    /**
     * 流程分类主键
     */
    @ApiModelProperty(value = "流程分类主键", required = true)
    @NotBlank(message = "流程分类主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private String categoryId;

    /**
     * 任务主键
     */
    @ApiModelProperty(value = "任务主键", required = true)
    @NotBlank(message = "任务主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private String taskId;

    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键", required = true)
    @NotNull(message = "用户主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 发起人Id
     */
    @ApiModelProperty(value = "发起人主键", required = true)
    @NotNull(message = "发起人主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long originatorId;
    /**
     * 发起人名称
     */
    @ApiModelProperty(value = "发起人名称", required = true)
    @NotNull(message = "发起人名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String originatorName;
}
