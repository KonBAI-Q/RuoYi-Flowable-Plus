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

import java.util.Map;

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

    private final IWfInstanceService flowInstanceService;

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/startBy/{procDefId}")
    public R startById(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                       @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        flowInstanceService.startProcessInstanceById(procDefId, variables);
        return R.ok("流程启动成功");
    }


    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public R updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                         @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
        flowInstanceService.updateState(state, instanceId);
        return R.ok();
    }

    @ApiOperation("结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    public R stopProcessInstance(@RequestBody WfTaskBo bo) {
        flowInstanceService.stopProcessInstance(bo);
        return R.ok();
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public R delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId,
                    @ApiParam(value = "删除原因") @RequestParam(required = false) String deleteReason) {
        flowInstanceService.delete(instanceId, deleteReason);
        return R.ok();
    }
}
