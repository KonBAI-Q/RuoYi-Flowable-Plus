package org.dromara.workflow.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.workflow.domain.WfCategory;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程分类视图对象 flow_category
 *
 * @author KonBAI
 * @date 2022-01-15
 */
@Data
@AutoMapper(target = WfCategory.class)
public class WfCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
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


}
