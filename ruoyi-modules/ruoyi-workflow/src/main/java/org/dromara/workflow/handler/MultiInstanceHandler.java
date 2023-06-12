package org.dromara.workflow.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import org.dromara.common.core.service.UserService;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.flowable.common.constant.ProcessConstants;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 多实例处理类
 *
 * @author KonBAI
 */
@AllArgsConstructor
@Component("multiInstanceHandler")
public class MultiInstanceHandler {

    private static final UserService userService = SpringUtils.getBean(UserService.class);

    public Set<String> getUserIds(DelegateExecution execution) {
        Set<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                // 添加候选用户id
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                // 获取组的ID，角色ID集合或部门ID集合
                List<Long> groups = userTask.getCandidateGroups().stream()
                    .map(item -> Long.parseLong(item.substring(4)))
                    .collect(Collectors.toList());
                List<Long> userIds = new ArrayList<>();
                if ("ROLES".equals(dataType)) {
                    // 通过角色id，获取所有用户id集合
                    userIds = userService.selectUserIdsByRoleIds(groups);
                } else if ("DEPTS".equals(dataType)) {
                    // 通过部门id，获取所有用户id集合
                    userIds = userService.selectUserIdsByDeptIds(groups);
                }
                // 添加候选用户id
                userIds.forEach(id -> candidateUserIds.add(String.valueOf(id)));
            }
        }
        return candidateUserIds;
    }
}
