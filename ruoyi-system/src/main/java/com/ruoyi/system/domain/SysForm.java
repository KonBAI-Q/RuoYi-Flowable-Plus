package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流程表单对象 sys_task_form
 *
 * @author XuanXuan Xuan
 * @date 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysForm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
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
    private String formContent;

    /**
     * 备注
     */
    private String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("formId", getFormId())
            .append("formName", getFormName())
            .append("formContent", getFormContent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
