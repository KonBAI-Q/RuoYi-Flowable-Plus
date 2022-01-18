package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.bo.FlowCategoryBo;
import com.ruoyi.workflow.domain.vo.FlowCategoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 流程分类Service接口
 *
 * @author KonBAI
 * @date 2022-01-15
 */
public interface IFlowCategoryService {
    /**
     * 查询单个
     * @return
     */
    FlowCategoryVo queryById(Long categoryId);

    /**
     * 查询列表
     */
    TableDataInfo<FlowCategoryVo> queryPageList(FlowCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询列表
     */
    List<FlowCategoryVo> queryList(FlowCategoryBo bo);

    /**
     * 根据新增业务对象插入【请填写功能名称】
     * @param bo 【请填写功能名称】新增业务对象
     * @return
     */
    Boolean insertByBo(FlowCategoryBo bo);

    /**
     * 根据编辑业务对象修改【请填写功能名称】
     * @param bo 【请填写功能名称】编辑业务对象
     * @return
     */
    Boolean updateByBo(FlowCategoryBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
