package org.dromara.workflow.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.workflow.domain.bo.WfCategoryBo;
import org.dromara.workflow.domain.vo.WfCategoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service接口
 *
 * @author KonBAI
 * @date 2022-01-15
 */
public interface IWfCategoryService {
    /**
     * 查询单个
     * @return
     */
    WfCategoryVo queryById(Long categoryId);

    /**
     * 查询列表
     */
    TableDataInfo<WfCategoryVo> queryPageList(WfCategoryBo categoryBo, PageQuery pageQuery);

    /**
     * 查询列表
     */
    List<WfCategoryVo> queryList(WfCategoryBo categoryBo);

    /**
     * 新增流程分类
     *
     * @param categoryBo 流程分类信息
     * @return 结果
     */
    int insertCategory(WfCategoryBo categoryBo);

    /**
     * 编辑流程分类
     * @param categoryBo 流程分类信息
     * @return 结果
     */
    int updateCategory(WfCategoryBo categoryBo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    int deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验分类编码是否唯一
     *
     * @param categoryBo 流程分类
     * @return 结果
     */
    boolean checkCategoryCodeUnique(WfCategoryBo categoryBo);
}
