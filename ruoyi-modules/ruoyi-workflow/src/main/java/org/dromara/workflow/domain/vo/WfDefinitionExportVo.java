package org.dromara.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 流程定义视图对象导出VO
 *
 * @author Baymax
 * @date 2023/6/19 22:42
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfDefinitionVo.class, convertGenerate = false)
public class WfDefinitionExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    @ExcelProperty(value = "流程定义ID")
    private String definitionId;

    /**
     * 流程名称
     */
    @ExcelProperty(value = "流程名称")
    private String processName;

    /**
     * 流程Key
     */
    @ExcelProperty(value = "流程Key")
    private String processKey;

    /**
     * 分类编码
     */
    @ExcelProperty(value = "分类编码")
    private String category;

    /**
     * 版本
     */
    @ExcelProperty(value = "版本")
    private Integer version;

    /**
     * 表单ID
     */
    @ExcelProperty(value = "表单ID")
    private Long formId;

    /**
     * 表单名称
     */
    @ExcelProperty(value = "表单名称")
    private String formName;

    /**
     * 部署ID
     */
    @ExcelProperty(value = "部署ID")
    private String deploymentId;

    /**
     * 流程是否暂停（true:挂起 false:激活 ）
     */
    @ExcelProperty(value = "流程是否挂起", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "true=挂起,false=激活")
    private Boolean suspended;

    /**
     * 部署时间
     */
    @ExcelProperty(value = "部署时间")
    private Date deploymentTime;
}
