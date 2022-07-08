package com.ruoyi.workflow.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.bo.WfModelBo;
import com.ruoyi.workflow.domain.vo.WfModelVo;

import java.util.Collection;

/**
 * @author KonBAI
 * @createTime 2022/6/21 9:11
 */
public interface IWfModelService {

    TableDataInfo<WfModelVo> list(WfModelBo modelBo, PageQuery pageQuery);

    TableDataInfo<WfModelVo> historyList(WfModelBo modelBo, PageQuery pageQuery);

    WfModelVo getModel(String modelId);

    String queryBpmnXmlById(String modelId);

    void saveModel(WfModelBo modelBo);

    void latestModel(String modelId);

    void deleteByIds(Collection<String> ids);

    void deployModel(String modelId);
}
