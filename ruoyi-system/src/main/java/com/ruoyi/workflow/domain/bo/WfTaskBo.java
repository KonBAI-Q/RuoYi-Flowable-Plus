package com.ruoyi.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程任务业务对象
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Data
@ApiModel("流程任务业务对象")
public class WfTaskBo {

    @ApiModelProperty("任务Id")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty("任务意见")
    private String comment;

    @ApiModelProperty("流程实例Id")
    private String instanceId;

    @ApiModelProperty("节点")
    private String targetKey;

    @ApiModelProperty("流程变量信息")
    private Map<String, Object> variables;

    @ApiModelProperty("审批人")
    private String assignee;

    @ApiModelProperty("候选人")
    private List<String> candidateUsers;

    @ApiModelProperty("审批组")
    private List<String> candidateGroups;

    @ApiModelProperty("抄送用户Id")
    private String copyUserIds;
}
