package com.ruoyi.workflow.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 任务追踪视图类
 *
 * @author KonBAI
 * @createTime 2022/1/8 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowViewerVo {

    /**
     * 获取流程实例的历史节点（去重）
     */
    private List<String> finishedTaskList;

    /**
     * 获取流程实例当前正在待办的节点（去重）
     */
    private List<String> unfinishedTaskList;
}
