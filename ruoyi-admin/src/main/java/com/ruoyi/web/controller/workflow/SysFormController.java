package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.workflow.service.ISysDeployFormService;
import com.ruoyi.workflow.service.ISysFormService;
import com.ruoyi.system.domain.SysDeployForm;
import com.ruoyi.system.domain.SysForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 流程表单Controller
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/workflow/form")
public class SysFormController extends BaseController {
    @Autowired
    private ISysFormService SysFormService;

    @Autowired
    private ISysDeployFormService sysDeployFormService;

    /**
     * 查询流程表单列表
     */
    @SaCheckPermission("flowable:form:list")
    @GetMapping("/list")
    public TableDataInfo list(SysForm sysForm, PageQuery pageQuery) {
        return SysFormService.selectSysFormPage(sysForm, pageQuery);
    }

    /**
     * 导出流程表单列表
     */
    @SaCheckPermission("flowable:form:export")
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(SysForm sysForm, HttpServletResponse response) {
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        ExcelUtil.exportExcel(list, "form", SysForm.class, response);
    }

    /**
     * 获取流程表单详细信息
     */
    @SaCheckPermission("flowable:form:query")
    @GetMapping(value = "/{formId}")
    public R getInfo(@PathVariable("formId") Long formId) {
        return R.ok(SysFormService.selectSysFormById(formId));
    }

    /**
     * 新增流程表单
     */
    @SaCheckPermission("flowable:form:add")
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.insertSysForm(sysForm));
    }

    /**
     * 修改流程表单
     */
    @SaCheckPermission("flowable:form:edit")
    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.updateSysForm(sysForm));
    }

    /**
     * 删除流程表单
     */
    @SaCheckPermission("flowable:form:remove")
    @Log(title = "流程表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{formIds}")
    public R remove(@PathVariable Long[] formIds) {
        return toAjax(SysFormService.deleteSysFormByIds(formIds));
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/addDeployForm")
    public R addDeployForm(@RequestBody SysDeployForm sysDeployForm) {
        return toAjax(sysDeployFormService.insertSysDeployForm(sysDeployForm));
    }
}
