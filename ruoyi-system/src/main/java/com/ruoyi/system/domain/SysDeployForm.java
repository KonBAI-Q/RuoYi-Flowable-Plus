package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程实例关联表单对象 sys_instance_form
 *
 * @author XuanXuan Xuan
 * @date 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeployForm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单主键
     */
    @ExcelProperty(value = "表单主键")
    private Long formId;

    /**
     * 流程定义主键
     */
    @ExcelProperty(value = "流程定义主键")
    private String deployId;
}
