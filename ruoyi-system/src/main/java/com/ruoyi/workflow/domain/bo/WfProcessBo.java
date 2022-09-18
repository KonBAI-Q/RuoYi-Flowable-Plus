package com.ruoyi.workflow.domain.bo;

import lombok.Data;

/**
 * 流程业务对象
 *
 * @author KonBAI
 * @createTime 2022/6/11 01:15
 */
@Data
public class WfProcessBo {

    /**
     * 流程标识
     */
    private String processKey;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 状态
     */
    private String state;
}
