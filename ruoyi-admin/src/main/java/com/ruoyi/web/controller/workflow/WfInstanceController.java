package com.ruoyi.web.controller.workflow;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.service.IWfInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流流程实例管理
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Slf4j
@Api(tags = "工作流流程实例管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
public class WfInstanceController {

    private final IWfInstanceService instanceService;

    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public R updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                         @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
        instanceService.updateState(state, instanceId);
        return R.ok();
    }

    @ApiOperation("结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    public R stopProcessInstance(@RequestBody WfTaskBo bo) {
        instanceService.stopProcessInstance(bo);
        return R.ok();
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public R delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId,
                    @ApiParam(value = "删除原因") @RequestParam(required = false) String deleteReason) {
        instanceService.delete(instanceId, deleteReason);
        return R.ok();
    }

    @ApiOperation(value = "查询流程实例详情信息")
    @GetMapping("/detail")
    public R detail(String procInsId, String deployId) {
        return R.ok(instanceService.queryDetailProcess(procInsId, deployId));
    }
}
