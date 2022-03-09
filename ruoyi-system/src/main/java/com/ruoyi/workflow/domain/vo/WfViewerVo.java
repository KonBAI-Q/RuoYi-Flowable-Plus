package com.ruoyi.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 任务追踪视图对象
 *
 * @author KonBAI
 * @createTime 2022/1/8 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("任务追踪视图对象")
@ExcelIgnoreUnannotated
public class WfViewerVo {

    /**
     * 获取流程实例的历史节点（去重）
     */
    private List<String> finishedTaskList;

    /**
     * 获取流程实例当前正在待办的节点（去重）
     */
    private List<String> unfinishedTaskList;
}
