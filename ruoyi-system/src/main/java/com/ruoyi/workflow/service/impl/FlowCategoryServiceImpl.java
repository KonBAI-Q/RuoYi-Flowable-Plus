package com.ruoyi.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.workflow.domain.FlowCategory;
import com.ruoyi.workflow.domain.bo.FlowCategoryBo;
import com.ruoyi.workflow.domain.vo.FlowCategoryVo;
import com.ruoyi.workflow.mapper.FlowCategoryMapper;
import com.ruoyi.workflow.service.IFlowCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 流程分类Service业务层处理
 *
 * @author KonBAI
 * @date 2022-01-15
 */
@RequiredArgsConstructor
@Service
public class FlowCategoryServiceImpl implements IFlowCategoryService {

    private final FlowCategoryMapper baseMapper;

    @Override
    public FlowCategoryVo queryById(Long categoryId){
        return baseMapper.selectVoById(categoryId);
    }

    @Override
    public TableDataInfo<FlowCategoryVo> queryPageList(FlowCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowCategory> lqw = buildQueryWrapper(bo);
        Page<FlowCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<FlowCategoryVo> queryList(FlowCategoryBo bo) {
        LambdaQueryWrapper<FlowCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowCategory> buildQueryWrapper(FlowCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FlowCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), FlowCategory::getCode, bo.getCode());
        return lqw;
    }

    @Override
    public Boolean insertByBo(FlowCategoryBo bo) {
        FlowCategory add = BeanUtil.toBean(bo, FlowCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCategoryId(add.getCategoryId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(FlowCategoryBo bo) {
        FlowCategory update = BeanUtil.toBean(bo, FlowCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(FlowCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
