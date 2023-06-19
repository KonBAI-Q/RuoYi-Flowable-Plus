package org.dromara.workflow.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.workflow.domain.WfCategory;
import org.dromara.workflow.domain.bo.WfCategoryBo;
import org.dromara.workflow.domain.vo.WfCategoryVo;
import org.dromara.workflow.mapper.WfCategoryMapper;
import org.dromara.workflow.service.IWfCategoryService;
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
    public TableDataInfo<WfCategoryVo> queryPageList(WfCategoryBo categoryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(categoryBo);
        Page<WfCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<WfCategoryVo> queryList(WfCategoryBo categoryBo) {
        LambdaQueryWrapper<WfCategory> lqw = buildQueryWrapper(categoryBo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfCategory> buildQueryWrapper(WfCategoryBo categoryBo) {
        Map<String, Object> params = categoryBo.getParams();
        LambdaQueryWrapper<WfCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(categoryBo.getCategoryName()), WfCategory::getCategoryName, categoryBo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(categoryBo.getCode()), WfCategory::getCode, categoryBo.getCode());
        return lqw;
    }

    @Override
    public int insertCategory(WfCategoryBo categoryBo) {
        WfCategory add = MapstructUtils.convert(categoryBo, WfCategory.class);
        return baseMapper.insert(add);
    }

    @Override
    public int updateCategory(WfCategoryBo categoryBo) {
        WfCategory update = MapstructUtils.convert(categoryBo, WfCategory.class);
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
     * @param categoryBo 流程分类
     * @return 结果
     */
    @Override
    public boolean checkCategoryCodeUnique(WfCategoryBo categoryBo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<WfCategory>()
            .eq(WfCategory::getCode, categoryBo.getCode())
            .ne(ObjectUtil.isNotNull(categoryBo.getCategoryId()), WfCategory::getCategoryId, categoryBo.getCategoryId()));
        return !exist;
    }
}
