package org.dromara.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.workflow.service.IWfIdentityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 身份控制器
 *
 * @author KonBAI
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/identity")
public class WfIdentityController {

    private final IWfIdentityService identityService;

    @GetMapping("deptTree")
    public R<List<Tree<Long>>> deptTree() {
        return R.ok(identityService.selectDeptTreeList());
    }

    /**
     * 查询用户列表，用于用户选择场景
     */
    @SaCheckLogin
    @GetMapping("/selectUser")
    public TableDataInfo<Map<String, Object>> selectUser(Long deptId, PageQuery pageQuery) {
        return identityService.selectPageUserList(deptId, pageQuery);
    }
}
