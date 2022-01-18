package com.ruoyi.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 流程分类对象 workflow_category
 *
 * @author KonBAI
 * @date 2022-01-15
 */
@Data
@Accessors(chain = true)
@TableName("workflow_category")
public class FlowCategory extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 分类ID
     */
    @TableId(value = "category_id")
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类编码
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
