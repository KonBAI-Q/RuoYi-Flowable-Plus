package com.ruoyi.flowable.utils;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.util.io.StringStreamSource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author KonBAI
 * @createTime 2022/3/26 19:04
 */
public class ModelUtils {

    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    /**
     * xml转bpmnModel对象
     *
     * @param xml xml
     * @return bpmnModel对象
     */
    public static BpmnModel getBpmnModel(String xml) {
        return bpmnXMLConverter.convertToBpmnModel(new StringStreamSource(xml), false, false);
    }

    /**
     * bpmnModel转xml对象
     *
     * @param bpmnModel bpmnModel对象
     * @return xml
     */
    public static byte[] getBpmnXml(BpmnModel bpmnModel) {
        return bpmnXMLConverter.convertToXML(bpmnModel);
    }

    /**
     * 获取开始节点
     *
     * @param model bpmnModel对象
     * @return 开始节点（未找到开始节点，返回null）
     */
    public static StartEvent getStartEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        return getStartEvent(flowElements);
    }

    /**
     * 获取开始节点
     *
     * @param flowElements 流程元素集合
     * @return 开始节点（未找到开始节点，返回null）
     */
    public static StartEvent getStartEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                return (StartEvent) flowElement;
            }
        }
        return null;
    }

    /**
     * 获取所有用户任务节点
     *
     * @param model bpmnModel对象
     * @return 用户任务节点列表
     */
    public static Collection<UserTask> getAllUserTaskEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        return getAllUserTaskEvent(flowElements, null);
    }

    /**
     * 获取所有用户任务节点
     * @param flowElements 流程元素集合
     * @param allElements 所有流程元素集合
     * @return 用户任务节点列表
     */
    public static Collection<UserTask> getAllUserTaskEvent(Collection<FlowElement> flowElements, Collection<UserTask> allElements) {
        allElements = allElements == null ? new ArrayList<>() : allElements;
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                allElements.add((UserTask) flowElement);
            }
            if (flowElement instanceof SubProcess) {
                // 继续深入子流程，进一步获取子流程
                allElements = getAllUserTaskEvent(((SubProcess) flowElement).getFlowElements(), allElements);
            }
        }
        return allElements;
    }
}
