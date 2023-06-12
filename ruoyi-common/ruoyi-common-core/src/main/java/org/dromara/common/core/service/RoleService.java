package org.dromara.common.core.service;

/**
 * 通用 角色服务
 *
 * @author KonBAI
 */
public interface RoleService {

    /**
     * 通过角色ID查询角色名称
     *
     * @param roleId 角色ID
     * @return 角色名称
     */
    String selectRoleNameById(Long roleId);
}
