package com.ruoyi.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.workflow.domain.WfCategory;
import com.ruoyi.workflow.domain.vo.WfCategoryVo;
import com.ruoyi.workflow.mapper.WfCategoryMapper;
import com.ruoyi.workflow.service.IWfCategoryService;
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
public class WfCategoryServiceImpl implements IWfCategoryService {

    private final WfCategoryMapper baseMapper;

    @Override
    public WfCategoryVo queryById(Long categoryId){
        return baseMapper.selectVoById(categoryId);
    }

    @Override
    public TableDataInfo<WfCategoryVo> queryPageList(WfCategory category, PageQuery pageQuery) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(category);
        Page<WfCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<WfCategoryVo> queryList(WfCategory category) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(category);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfCategory> buildQueryWrapper(WfCategory category) {
        Map<String, Object> params = category.getParams();
        LambdaQueryWrapper<WfCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(category.getCategoryName()), WfCategory::getCategoryName, category.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(category.getCode()), WfCategory::getCode, category.getCode());
        return lqw;
    }

    @Override
    public int insertCategory(WfCategory categoryBo) {
        WfCategory add = BeanUtil.toBean(categoryBo, WfCategory.class);
        return baseMapper.insert(add);
    }

    @Override
    public int updateCategory(WfCategory categoryBo) {
        WfCategory update = BeanUtil.toBean(categoryBo, WfCategory.class);
        return baseMapper.updateById(update);
    }

    @Override
    public int deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * 校验分类编码是否唯一
     *
     * @param category 流程分类
     * @return 结果
     */
    @Override
    public boolean checkCategoryCodeUnique(WfCategory category) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<WfCategory>()
            .eq(WfCategory::getCode, category.getCode())
            .ne(ObjectUtil.isNotNull(category.getCategoryId()), WfCategory::getCategoryId, category.getCategoryId()));
        return !exist;
    }
}
