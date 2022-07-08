package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.workflow.domain.bo.WfModelBo;
import com.ruoyi.workflow.domain.vo.WfModelVo;
import com.ruoyi.workflow.service.IWfModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author KonBAI
 * @createTime 2022/6/21 9:09
 */
@Slf4j
@Api(tags = "工作流流程模型管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/model")
public class WfModelController extends BaseController {

    private final IWfModelService modelService;

    @ApiOperation(value = "查询流程模型列表")
    @SaCheckPermission("workflow:model:list")
    @GetMapping("/list")
    public TableDataInfo<WfModelVo> list(WfModelBo modelBo, PageQuery pageQuery) {
        return modelService.list(modelBo, pageQuery);
    }

    @ApiOperation(value = "查询流程模型列表")
    @SaCheckPermission("workflow:model:list")
    @GetMapping("/historyList")
    public TableDataInfo<WfModelVo> historyList(WfModelBo modelBo, PageQuery pageQuery) {
        return modelService.historyList(modelBo, pageQuery);
    }

    /**
     * 获取流程模型详细信息
     */
    @ApiOperation(value = "查询流程模型详情信息")
    @SaCheckPermission("workflow:model:query")
    @GetMapping(value = "/{modelId}")
    public R<WfModelVo> getInfo(@ApiParam("主键") @NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        return R.ok(modelService.getModel(modelId));
    }

    /**
     * 获取流程表单详细信息
     */
    @SaCheckPermission("workflow:model:query")
    @GetMapping(value = "/bpmnXml/{modelId}")
    public R<String> getBpmnXml(@ApiParam("主键") @NotNull(message = "主键不能为空") @PathVariable("modelId") String modelId) {
        return R.ok("操作成功", modelService.queryBpmnXmlById(modelId));
    }

    /**
     * 保存流程模型
     */
    @ApiOperation("保存流程模型")
    @SaCheckPermission("workflow:model:save")
    @Log(title = "保存流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<String> save(@RequestBody WfModelBo modelBo) {
        modelService.saveModel(modelBo);
        return R.ok();
    }

    @ApiOperation("设为最新流程模型")
    @SaCheckPermission("workflow:model:save")
    @Log(title = "设为最新流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/latest")
    public R<?> latest(@RequestParam String modelId) {
        modelService.latestModel(modelId);
        return R.ok();
    }

    /**
     * 删除流程模型
     */
    @ApiOperation("删除流程模型")
    @SaCheckPermission("workflow:model:remove")
    @Log(title = "删除流程模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelIds}")
    public R<String> remove(@ApiParam("主键串") @NotEmpty(message = "主键不能为空") @PathVariable String[] modelIds) {
        modelService.deleteByIds(Arrays.asList(modelIds));
        return R.ok();
    }

    @ApiOperation("部署流程模型")
    @SaCheckPermission("workflow:model:deploy")
    @Log(title = "部署流程模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/deploy")
    public R<Void> deployModel(@RequestParam String modelId) {
        modelService.deployModel(modelId);
        return R.ok();
    }

}
