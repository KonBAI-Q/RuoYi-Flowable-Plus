package com.ruoyi.workflow.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程业务对象
 *
 * @author KonBAI
 * @createTime 2022/6/11 01:15
 */
@Data
@ApiModel("流程业务对象")
public class WfProcessBo {

    @ApiModelProperty("流程标识")
    private String processKey;

    @ApiModelProperty("流程名称")
    private String processName;

    @ApiModelProperty("流程分类")
    private String category;

    @ApiModelProperty("状态")
    private String state;
}
