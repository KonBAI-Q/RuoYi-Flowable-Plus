package com.ruoyi.workflow.domain.vo;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.flowable.core.FormConf;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 流程详情视图对象
 *
 * @author KonBAI
 * @createTime 2022/8/7 15:01
 */
@Data
@ApiModel("流程详情视图对象")
public class WfDetailVo {

    /**
     * 任务表单信息
     */
    private FormConf taskFormData;

    /**
     * 历史任务信息
     */
    private List<WfTaskVo> historyTaskList;

    /**
     * 流程表单列表
     */
    private List<FormConf> processFormList;

    /**
     * 是否存在任务表单信息
     * @return true:存在；false:不存在
     */
    public Boolean isExistTaskForm() {
        return ObjectUtil.isNotEmpty(this.taskFormData);
    }
}
