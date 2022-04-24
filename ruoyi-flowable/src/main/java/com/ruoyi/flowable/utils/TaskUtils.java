package com.ruoyi.flowable.utils;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.flowable.common.constant.TaskConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作流任务工具类
 *
 * @author konbai
 * @createTime 2022/4/24 12:42
 */
public class TaskUtils {

    public static String getUserId() {
        return String.valueOf(LoginHelper.getUserId());
    }

    /**
     * 获取用户组信息
     *
     * @return candidateGroup
     */
    public static List<String> getCandidateGroup() {
        List<String> list = new ArrayList<>();
        LoginUser user = LoginHelper.getLoginUser();
        if (ObjectUtil.isNotNull(user)) {
            if (ObjectUtil.isNotEmpty(user.getRoles())) {
                user.getRoles().forEach(role -> list.add(TaskConstants.ROLE_GROUP_PREFIX + role.getRoleId()));
            }
            if (ObjectUtil.isNotNull(user.getDeptId())) {
                list.add(TaskConstants.DEPT_GROUP_PREFIX + user.getDeptId());
            }
        }
        return list;
    }
}
