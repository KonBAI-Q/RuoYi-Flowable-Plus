package com.ruoyi.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.workflow.domain.WfDeployForm;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.mapper.WfDeployFormMapper;
import com.ruoyi.workflow.mapper.WfFormMapper;
import com.ruoyi.workflow.service.IWfDeployFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程实例关联表单Service业务层处理
 *
 * @author KonBAI
 * @createTime 2022/3/7 22:07
 */
@RequiredArgsConstructor
@Service
public class WfDeployFormServiceImpl implements IWfDeployFormService {

    private final WfDeployFormMapper baseMapper;

    private final WfFormMapper formMapper;

    /**
     * 新增流程实例关联表单
     *
     * @param deployForm 流程实例关联表单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWfDeployForm(WfDeployForm deployForm) {
        // 删除部署流程和表单的关联关系
        baseMapper.delete(new LambdaQueryWrapper<WfDeployForm>().eq(WfDeployForm::getDeployId, deployForm.getDeployId()));
        // 新增部署流程和表单关系
        return baseMapper.insert(deployForm);
    }

    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    @Override
    public WfFormVo selectSysDeployFormByDeployId(String deployId) {
        return formMapper.selectFormByDeployId(deployId);
    }
}
