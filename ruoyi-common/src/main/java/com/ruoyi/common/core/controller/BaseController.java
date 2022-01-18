package com.ruoyi.common.core.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.LoginUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
public class BaseController {

    /**
     * 返回成功
     */
    public R<Void> success() {
        return R.success();
    }

    /**
     * 返回失败消息
     */
    public R<Void> error() {
        return R.error();
    }

    /**
     * 返回成功消息
     */
    public R<Void> success(String message) {
        return R.success(message);
    }

    /**
     * 返回失败消息
     */
    public R<Void> error(String message) {
        return R.error(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R<Void> toAjax(int rows) {
        return rows > 0 ? R.success() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R<Void> toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUser getLoginUser() {
        return LoginUtils.getLoginUser();
    }

    /**
     * 获取登录用户id
     */
    public Long getUserId() {
        return LoginUtils.getUserId();
    }

    /**
     * 获取登录部门id
     */
    public Long getDeptId() {
        return LoginUtils.getDeptId();
    }

    /**
     * 获取登录用户名
     */
    public String getUsername() {
        return LoginUtils.getUsername();
    }
}
