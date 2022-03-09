package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.bo.WfCategoryBo;
import com.ruoyi.workflow.domain.vo.WfCategoryVo;

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
    TableDataInfo<WfCategoryVo> queryPageList(WfCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询列表
     */
    List<WfCategoryVo> queryList(WfCategoryBo bo);

    /**
     * 根据新增业务对象插入【请填写功能名称】
     * @param bo 【请填写功能名称】新增业务对象
     * @return
     */
    Boolean insertByBo(WfCategoryBo bo);

    /**
     * 根据编辑业务对象修改【请填写功能名称】
     * @param bo 【请填写功能名称】编辑业务对象
     * @return
     */
    Boolean updateByBo(WfCategoryBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
