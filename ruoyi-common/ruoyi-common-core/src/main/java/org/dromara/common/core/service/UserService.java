package org.dromara.common.core.service;

import java.util.List;

/**
 * 通用 用户服务
 *
 * @author Lion Li
 */
public interface UserService {

    /**
     * 通过用户ID查询用户账户
     *
     * @param userId 用户ID
     * @return 用户账户
     */
    String selectUserNameById(Long userId);

    /**
     * 通过用户ID查询用户昵称
     *
     * @param userId 用户ID
     * @return 用户昵称
     */
    String selectNickNameById(Long userId);

    /**
     * 通过角色ID集合查询用户ID集合
     *
     * @param roleIds 角色ID集合
     * @return 用户ID集合
     */
    List<Long> selectUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 通过部门ID集合查询用户ID集合
     *
     * @param deptIds 部门ID集合
     * @return 用户ID集合
     */
    List<Long> selectUserIdsByDeptIds(List<Long> deptIds);
}
