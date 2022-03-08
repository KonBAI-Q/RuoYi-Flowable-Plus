package com.ruoyi.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
@ApiModel("流程分类视图对象")
@ExcelIgnoreUnannotated
public class WfFormVo {

    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @ExcelProperty(value = "表单ID")
    @ApiModelProperty("表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @ExcelProperty(value = "表单名称")
    @ApiModelProperty("表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @ExcelProperty(value = "表单内容")
    @ApiModelProperty("表单内容")
    private String content;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;
}
