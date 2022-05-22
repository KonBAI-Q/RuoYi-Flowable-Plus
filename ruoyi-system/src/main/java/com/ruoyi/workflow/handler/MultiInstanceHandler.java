package com.ruoyi.workflow.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import lombok.AllArgsConstructor;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多实例处理类
 *
 * @author KonBAI
 */
@AllArgsConstructor
@Component("multiInstanceHandler")
public class MultiInstanceHandler {

    public HashSet<String> getUserIds(DelegateExecution execution) {
        HashSet<String> candidateUserIds = new LinkedHashSet<>();
        FlowElement flowElement = execution.getCurrentFlowElement();
        if (ObjectUtil.isNotEmpty(flowElement) && flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
            if ("USERS".equals(dataType) && CollUtil.isNotEmpty(userTask.getCandidateUsers())) {
                candidateUserIds.addAll(userTask.getCandidateUsers());
            } else if (CollUtil.isNotEmpty(userTask.getCandidateGroups())) {
                List<String> groups = userTask.getCandidateGroups()
                    .stream().map(item -> item.substring(4)).collect(Collectors.toList());
                if ("ROLES".equals(dataType)) {
                    SysUserRoleMapper userRoleMapper = SpringUtils.getBean(SysUserRoleMapper.class);
                    groups.forEach(item -> {
                        List<String> userIds = userRoleMapper.selectUserIdsByRoleId(Long.parseLong(item))
                            .stream().map(String::valueOf).collect(Collectors.toList());
                        candidateUserIds.addAll(userIds);
                    });
                } else if ("DEPTS".equals(dataType)) {
                    SysUserMapper userMapper = SpringUtils.getBean(SysUserMapper.class);
                    LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<SysUser>()
                        .select(SysUser::getUserId).in(SysUser::getDeptId, groups);
                    List<String> userIds = userMapper.selectList(lambdaQueryWrapper)
                        .stream().map(k -> String.valueOf(k.getUserId())).collect(Collectors.toList());
                    candidateUserIds.addAll(userIds);
                }
            }
        }
        return candidateUserIds;
    }
}
