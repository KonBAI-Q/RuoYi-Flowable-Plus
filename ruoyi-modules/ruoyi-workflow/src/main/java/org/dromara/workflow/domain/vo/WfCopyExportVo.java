package org.dromara.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程抄送视图对象导出VO
 *
 * @author Baymax
 * @date 2022/6/19 22:41
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfCopyVo.class, convertGenerate = false)
public class WfCopyExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 抄送主键
     */
    @ExcelProperty(value = "抄送主键")
    private Long copyId;

    /**
     * 抄送标题
     */
    @ExcelProperty(value = "抄送标题")
    private String title;

    /**
     * 流程主键
     */
    @ExcelProperty(value = "流程主键")
    private String processId;

    /**
     * 流程名称
     */
    @ExcelProperty(value = "流程名称")
    private String processName;

    /**
     * 流程分类主键
     */
    @ExcelProperty(value = "流程分类主键")
    private String categoryId;

    /**
     * 部署主键
     */
    @ExcelProperty(value = "部署主键")
    private String deploymentId;

    /**
     * 流程实例主键
     */
    @ExcelProperty(value = "流程实例主键")
    private String instanceId;

    /**
     * 任务主键
     */
    @ExcelProperty(value = "任务主键")
    private String taskId;

    /**
     * 用户主键
     */
    @ExcelProperty(value = "用户主键")
    private Long userId;

    /**
     * 发起人Id
     */
    @ExcelProperty(value = "发起人主键")
    private Long originatorId;

    /**
     * 发起人名称
     */
    @ExcelProperty(value = "发起人名称")
    private String originatorName;

    /**
     * 抄送时间（创建时间）
     */
    @ExcelProperty(value = "抄送时间")
    private Date createTime;
}
