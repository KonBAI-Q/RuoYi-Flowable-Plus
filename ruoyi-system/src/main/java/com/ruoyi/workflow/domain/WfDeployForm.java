package com.ruoyi.workflow.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 流程实例关联表单对象 sys_instance_form
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@Data
@TableName("wf_deploy_form")
@ApiModel("部署实例和表单关联")
public class WfDeployForm {
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义主键
     */
    @TableId(type = IdType.INPUT)
    @ExcelProperty(value = "流程定义主键")
    private String deployId;

    /**
     * 表单主键
     */
    @ExcelProperty(value = "表单主键")
    private Long formId;
}
