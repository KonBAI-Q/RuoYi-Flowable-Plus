package com.ruoyi.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author KonBAI
 * @createTime 2022/6/21 9:16
 */
@Data
@ApiModel("流程模型视图对象")
@ExcelIgnoreUnannotated
public class WfModelVo {

    @ExcelProperty(value = "模型ID")
    @ApiModelProperty("模型ID")
    private String modelId;

    @ExcelProperty(value = "模型名称")
    @ApiModelProperty("模型名称")
    private String modelName;

    @ExcelProperty(value = "模型Key")
    @ApiModelProperty("模型Key")
    private String modelKey;

    @ExcelProperty(value = "分类编码")
    @ApiModelProperty("分类编码")
    private String category;

    @ExcelProperty(value = "版本")
    @ApiModelProperty("版本")
    private Integer version;

    @ExcelProperty(value = "表单类型")
    @ApiModelProperty("表单类型")
    private Integer formType;

    @ExcelProperty(value = "表单ID")
    @ApiModelProperty("表单ID")
    private Long formId;

    @ExcelProperty(value = "模型描述")
    @ApiModelProperty("模型描述")
    private String description;

    @ExcelProperty(value = "创建时间")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ExcelProperty(value = "流程xml")
    @ApiModelProperty("流程xml")
    private String bpmnXml;

    @ExcelProperty(value = "表单内容")
    @ApiModelProperty("表单内容")
    private String content;
}
