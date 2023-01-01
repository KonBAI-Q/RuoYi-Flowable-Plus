package com.ruoyi.flowable.utils;

import cn.hutool.core.util.ObjectUtil;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.util.io.StringStreamSource;

import java.util.*;

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
     * 根据节点，获取入口连线
     *
     * @param source 起始节点
     * @return 入口连线列表
     */
    public static List<SequenceFlow> getElementIncomingFlows(FlowElement source) {
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        if (source instanceof FlowNode) {
            sequenceFlows = ((FlowNode) source).getIncomingFlows();
        }
        return sequenceFlows;
    }


    /**
     * 根据节点，获取出口连线
     *
     * @param source 起始节点
     * @return 出口连线列表
     */
    public static List<SequenceFlow> getElementOutgoingFlows(FlowElement source) {
        List<SequenceFlow> sequenceFlows = new ArrayList<>();
        if (source instanceof FlowNode) {
            sequenceFlows = ((FlowNode) source).getOutgoingFlows();
        }
        return sequenceFlows;
    }

    /**
     * 获取开始节点
     *
     * @param model bpmnModel对象
     * @return 开始节点（未找到开始节点，返回null）
     */
    public static StartEvent getStartEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        FlowElement startElement = process.getInitialFlowElement();
        if (startElement instanceof StartEvent) {
            return (StartEvent) startElement;
        }
        return getStartEvent(process.getFlowElements());
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
     * 获取结束节点
     *
     * @param model bpmnModel对象
     * @return 结束节点（未找到开始节点，返回null）
     */
    public static EndEvent getEndEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        return getEndEvent(process.getFlowElements());
    }

    /**
     * 获取结束节点
     *
     * @param flowElements 流程元素集合
     * @return 结束节点（未找到开始节点，返回null）
     */
    public static EndEvent getEndEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof EndEvent) {
                return (EndEvent) flowElement;
            }
        }
        return null;
    }

    public static UserTask getUserTaskByKey(BpmnModel model, String taskKey) {
        Process process = model.getMainProcess();
        FlowElement flowElement = process.getFlowElement(taskKey);
        if (flowElement instanceof UserTask) {
            return (UserTask) flowElement;
        }
        return null;
    }

    public static boolean isMultiInstance(BpmnModel model, String taskKey) {
        UserTask userTask = getUserTaskByKey(model, taskKey);
        if (ObjectUtil.isNotNull(userTask)) {
            return userTask.hasMultiInstanceLoopCharacteristics();
        }
        return false;
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

    /**
     * 查找起始节点下一个用户任务列表列表
     * @param source 起始节点
     * @return 结果
     */
    public static List<UserTask> findNextUserTasks(FlowElement source) {
        return findNextUserTasks(source, null, null);
    }

    /**
     * 查找起始节点下一个用户任务列表列表
     * @param source 起始节点
     * @param hasSequenceFlow 已经经过的连线的 ID，用于判断线路是否重复
     * @param userTaskList 用户任务列表
     * @return 结果
     */
    public static List<UserTask> findNextUserTasks(FlowElement source, Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        hasSequenceFlow = Optional.ofNullable(hasSequenceFlow).orElse(new HashSet<>());
        userTaskList = Optional.ofNullable(userTaskList).orElse(new ArrayList<>());
        // 获取出口连线
        List<SequenceFlow> sequenceFlows = getElementOutgoingFlows(source);
        if (!sequenceFlows.isEmpty()) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                // 如果发现连线重复，说明循环了，跳过这个循环
                if (hasSequenceFlow.contains(sequenceFlow.getId())) {
                    continue;
                }
                // 添加已经走过的连线
                hasSequenceFlow.add(sequenceFlow.getId());
                FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                if (targetFlowElement instanceof UserTask) {
                    // 若节点为用户任务，加入到结果列表中
                    userTaskList.add((UserTask) targetFlowElement);
                } else {
                    // 若节点非用户任务，继续递归查找下一个节点
                    findNextUserTasks(targetFlowElement, hasSequenceFlow, userTaskList);
                }
            }
        }
        return userTaskList;
    }
}
