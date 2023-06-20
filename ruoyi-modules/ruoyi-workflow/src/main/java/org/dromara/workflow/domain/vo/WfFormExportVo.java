package org.dromara.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程分类视图对象导出VO
 *
 * @author Baymax
 * @date 2023/6/19 22:44
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfFormVo.class, convertGenerate = false)
public class WfFormExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @ExcelProperty(value = "表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @ExcelProperty(value = "表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @ExcelProperty(value = "表单内容")
    private String content;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
}
