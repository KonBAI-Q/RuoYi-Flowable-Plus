package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.workflow.domain.bo.WfCategoryBo;
import com.ruoyi.workflow.domain.vo.WfCategoryVo;
import com.ruoyi.workflow.service.IWfCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 流程分类Controller
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Validated
@Api(value = "流程分类控制器", tags = {"流程分类管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/category")
public class WfCategoryController extends BaseController {

    private final IWfCategoryService flowCategoryService;

    /**
     * 查询流程分类列表
     */
    @ApiOperation("查询流程分类列表")
    @SaCheckPermission("workflow:category:list")
    @GetMapping("/list")
    public TableDataInfo<WfCategoryVo> list(@Validated(QueryGroup.class) WfCategoryBo bo, PageQuery pageQuery) {
        return flowCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出流程分类列表
     */
    @ApiOperation("导出流程分类列表")
    @SaCheckPermission("workflow:category:export")
    @Log(title = "流程分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated WfCategoryBo bo, HttpServletResponse response) {
        List<WfCategoryVo> list = flowCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "流程分类", WfCategoryVo.class, response);
    }

    /**
     * 获取流程分类详细信息
     */
    @ApiOperation("获取流程分类详细信息")
    @SaCheckPermission("workflow:category:query")
    @GetMapping("/{categoryId}")
    public R<WfCategoryVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("categoryId") Long categoryId) {
        return R.ok(flowCategoryService.queryById(categoryId));
    }

    /**
     * 新增流程分类
     */
    @ApiOperation("新增流程分类")
    @SaCheckPermission("workflow:category:add")
    @Log(title = "流程分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfCategoryBo bo) {
        return toAjax(flowCategoryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改流程分类
     */
    @ApiOperation("修改流程分类")
    @SaCheckPermission("workflow:category:edit")
    @Log(title = "流程分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfCategoryBo bo) {
        return toAjax(flowCategoryService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除流程分类
     */
    @ApiOperation("删除流程分类")
    @SaCheckPermission("workflow:category:remove")
    @Log(title = "流程分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public R<Void> remove(@ApiParam("主键串") @NotEmpty(message = "主键不能为空") @PathVariable Long[] categoryIds) {
        return toAjax(flowCategoryService.deleteWithValidByIds(Arrays.asList(categoryIds), true) ? 1 : 0);
    }
}
