package com.ruoyi.web.controller.workflow;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.dto.WfNextDto;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.domain.bo.WfTaskBo;
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

    @Deprecated
    @ApiOperation(value = "我发起的流程", response = WfTaskVo.class)
    @GetMapping(value = "/myProcess")
    public TableDataInfo<WfTaskVo> myProcess(PageQuery pageQuery) {
        return flowTaskService.myProcess(pageQuery);
    }

    @ApiOperation(value = "取消申请", response = WfTaskVo.class)
    @PostMapping(value = "/stopProcess")
    public R stopProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.stopProcess(bo);
        return R.ok();
    }

    @ApiOperation(value = "撤回流程", response = WfTaskVo.class)
    @PostMapping(value = "/revokeProcess")
    public R revokeProcess(@RequestBody WfTaskBo bo) {
        flowTaskService.revokeProcess(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取待办列表", response = WfTaskVo.class)
    @GetMapping(value = "/todoList")
    public TableDataInfo<WfTaskVo> todoList(PageQuery pageQuery) {
        return flowTaskService.todoList(pageQuery);
    }

    @Deprecated
    @ApiOperation(value = "获取已办任务", response = WfTaskVo.class)
    @GetMapping(value = "/finishedList")
    public TableDataInfo<WfTaskVo> finishedList(PageQuery pageQuery) {
        return flowTaskService.finishedList(pageQuery);
    }


    @ApiOperation(value = "流程历史流转记录", response = WfTaskVo.class)
    @GetMapping(value = "/flowRecord")
    public R flowRecord(String procInsId, String deployId) {
        return R.ok(flowTaskService.flowRecord(procInsId, deployId));
    }

    @ApiOperation(value = "获取流程变量", response = WfTaskVo.class)
    @GetMapping(value = "/processVariables/{taskId}")
    public R processVariables(@ApiParam(value = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return R.ok(flowTaskService.getProcessVariables(taskId));
    }

    @ApiOperation(value = "审批任务")
    @PostMapping(value = "/complete")
    public R complete(@RequestBody WfTaskBo bo) {
        flowTaskService.complete(bo);
        return R.ok();
    }


    @ApiOperation(value = "驳回任务")
    @PostMapping(value = "/reject")
    public R taskReject(@RequestBody WfTaskBo bo) {
        flowTaskService.taskReject(bo);
        return R.ok();
    }

    @ApiOperation(value = "退回任务")
    @PostMapping(value = "/return")
    public R taskReturn(@RequestBody WfTaskBo bo) {
        flowTaskService.taskReturn(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    public R findReturnTaskList(@RequestBody WfTaskBo bo) {
        return R.ok(flowTaskService.findReturnTaskList(bo));
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestBody WfTaskBo bo) {
        flowTaskService.deleteTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "认领/签收任务")
    @PostMapping(value = "/claim")
    public R claim(@RequestBody WfTaskBo bo) {
        flowTaskService.claim(bo);
        return R.ok();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    public R unClaim(@RequestBody WfTaskBo bo) {
        flowTaskService.unClaim(bo);
        return R.ok();
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    public R delegate(@RequestBody WfTaskBo bo) {
        flowTaskService.delegateTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "转办任务")
    @PostMapping(value = "/assign")
    public R assign(@RequestBody WfTaskBo bo) {
        flowTaskService.assignTask(bo);
        return R.ok();
    }

    @ApiOperation(value = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
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
