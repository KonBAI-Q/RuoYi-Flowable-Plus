package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
import com.ruoyi.workflow.domain.dto.WfNextDto;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.service.IWfTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 工作流任务管理
 *
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Slf4j
@Api(tags = "工作流流程任务管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/task")
public class WfTaskController {

    private final IWfTaskService flowTaskService;

    @ApiOperation(value = "取消申请", response = WfTaskVo.class)
    @PostMapping(value = "/stopProcess")
    @SaCheckPermission("workflow:process:cancel")
    public R stopProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.stopProcess(bo);
        return R.ok();
    }

    @ApiOperation(value = "撤回流程", response = WfTaskVo.class)
    @PostMapping(value = "/revokeProcess")
    @SaCheckPermission("workflow:process:revoke")
    public R revokeProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.revokeProcess(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取流程变量", response = WfTaskVo.class)
    @GetMapping(value = "/processVariables/{taskId}")
    @SaCheckPermission("workflow:process:query")
    public R processVariables(@ApiParam(value = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return R.ok(flowTaskService.getProcessVariables(taskId));
    }

    @ApiOperation(value = "审批任务")
    @PostMapping(value = "/complete")
    @SaCheckPermission("workflow:process:approval")
    public R complete(@RequestBody WfTaskBo bo) {
        flowTaskService.complete(bo);
        return R.ok();
    }


    @ApiOperation(value = "驳回任务")
    @PostMapping(value = "/reject")
    @SaCheckPermission("workflow:process:approval")
    public R taskReject(@RequestBody WfTaskBo bo) {
        flowTaskService.taskReject(bo);
        return R.ok();
    }

    @ApiOperation(value = "退回任务")
    @PostMapping(value = "/return")
    @SaCheckPermission("workflow:process:approval")
    public R taskReturn(@RequestBody WfTaskBo bo) {
        flowTaskService.taskReturn(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    @SaCheckPermission("workflow:process:query")
    public R findReturnTaskList(@RequestBody WfTaskBo bo) {
        return R.ok(flowTaskService.findReturnTaskList(bo));
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping(value = "/delete")
    @SaCheckPermission("workflow:process:approval")
    public R delete(@RequestBody WfTaskBo bo) {
        flowTaskService.deleteTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "认领/签收任务")
    @PostMapping(value = "/claim")
    @SaCheckPermission("workflow:process:claim")
    public R claim(@RequestBody WfTaskBo bo) {
        flowTaskService.claim(bo);
        return R.ok();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    @SaCheckPermission("workflow:process:claim")
    public R unClaim(@RequestBody WfTaskBo bo) {
        flowTaskService.unClaim(bo);
        return R.ok();
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    @SaCheckPermission("workflow:process:approval")
    public R delegate(@RequestBody WfTaskBo bo) {
        if (ObjectUtil.hasNull(bo.getTaskId(), bo.getUserId())) {
            return R.fail("参数错误！");
        }
        flowTaskService.delegateTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "转办任务")
    @PostMapping(value = "/transfer")
    @SaCheckPermission("workflow:process:approval")
    public R transfer(@RequestBody WfTaskBo bo) {
        if (ObjectUtil.hasNull(bo.getTaskId(), bo.getUserId())) {
            return R.fail("参数错误！");
        }
        flowTaskService.transferTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
    @SaCheckPermission("workflow:process:query")
    public R getNextFlowNode(@RequestBody WfTaskBo bo) {
        WfNextDto wfNextDto = flowTaskService.getNextFlowNode(bo);
        return wfNextDto != null ? R.ok(wfNextDto) : R.ok("流程已完结", null);
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成流程图
     *
     * @param procInsId 任务ID
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public R getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return R.ok(flowTaskService.getFlowViewer(procInsId));
    }
}
