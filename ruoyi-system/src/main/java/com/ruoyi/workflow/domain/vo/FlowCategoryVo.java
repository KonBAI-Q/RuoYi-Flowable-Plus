package com.ruoyi.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 流程分类视图对象 flow_category
 *
 * @author KonBAI
 * @date 2022-01-15
 */
@Data
@ApiModel("流程分类视图对象")
@ExcelIgnoreUnannotated
public class FlowCategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    @ApiModelProperty("分类ID")
    private Long categoryId;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 分类编码
     */
    @ExcelProperty(value = "分类编码")
    @ApiModelProperty("分类编码")
    private String code;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;


}
