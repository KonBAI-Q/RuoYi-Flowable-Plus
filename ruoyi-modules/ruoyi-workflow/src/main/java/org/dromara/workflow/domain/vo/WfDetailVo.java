package org.dromara.workflow.domain.vo;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.dromara.common.flowable.core.FormConf;

import java.util.List;

/**
 * 流程详情视图对象
 *
 * @author KonBAI
 * @createTime 2022/8/7 15:01
 */
@Data
public class WfDetailVo {

    /**
     * 任务表单信息
     */
    private FormConf taskFormData;

    /**
     * 历史流程节点信息
     */
    private List<WfProcNodeVo> historyProcNodeList;

    /**
     * 流程表单列表
     */
    private List<FormConf> processFormList;

    /**
     * 流程XML
     */
    private String bpmnXml;

    private WfViewerVo flowViewer;

    /**
     * 是否存在任务表单信息
     * @return true:存在；false:不存在
     */
    public Boolean isExistTaskForm() {
        return ObjectUtil.isNotEmpty(this.taskFormData);
    }
}
