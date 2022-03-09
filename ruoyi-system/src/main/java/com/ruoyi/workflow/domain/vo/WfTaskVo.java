package com.ruoyi.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.workflow.domain.dto.WfCommentDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作流任务视图对象
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Data
@ApiModel("工作流任务视图对象")
@ExcelIgnoreUnannotated
public class WfTaskVo implements Serializable {

    @ApiModelProperty("任务编号")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务Key")
    private String taskDefKey;

    @ApiModelProperty("任务执行人Id")
    private Long assigneeId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("流程发起人部门名称")
    private String startDeptName;

    @ApiModelProperty("任务执行人名称")
    private String assigneeName;

    @ApiModelProperty("流程发起人Id")
    private String startUserId;

    @ApiModelProperty("流程发起人名称")
    private String startUserName;

    @ApiModelProperty("流程类型")
    private String category;

    @ApiModelProperty("流程变量信息")
    private Object procVars;

    @ApiModelProperty("局部变量信息")
    private Object taskLocalVars;

    @ApiModelProperty("流程部署编号")
    private String deployId;

    @ApiModelProperty("流程ID")
    private String procDefId;

    @ApiModelProperty("流程key")
    private String procDefKey;

    @ApiModelProperty("流程定义名称")
    private String procDefName;

    @ApiModelProperty("流程定义内置使用版本")
    private int procDefVersion;

    @ApiModelProperty("流程实例ID")
    private String procInsId;

    @ApiModelProperty("历史流程实例ID")
    private String hisProcInsId;

    @ApiModelProperty("任务耗时")
    private String duration;

    @ApiModelProperty("任务意见")
    private WfCommentDto comment;

    @ApiModelProperty("候选执行人")
    private String candidate;

    @ApiModelProperty("任务创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("任务完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

}
