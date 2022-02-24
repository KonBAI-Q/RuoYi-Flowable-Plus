package com.ruoyi.web.controller.workflow;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.dto.FlowTaskDto;
import com.ruoyi.workflow.domain.vo.FlowTaskVo;
import com.ruoyi.workflow.service.IFlowTaskService;
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
 * <p>工作流任务管理<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Slf4j
@Api(tags = "工作流流程任务管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/task")
public class FlowTaskController {

    private final IFlowTaskService flowTaskService;

    @ApiOperation(value = "我发起的流程", response = FlowTaskDto.class)
    @GetMapping(value = "/myProcess")
    public TableDataInfo<FlowTaskDto> myProcess(PageQuery pageQuery) {
        return flowTaskService.myProcess(pageQuery);
    }

    @ApiOperation(value = "取消申请", response = FlowTaskDto.class)
    @PostMapping(value = "/stopProcess")
    public R stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.stopProcess(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "撤回流程", response = FlowTaskDto.class)
    @PostMapping(value = "/revokeProcess")
    public R revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.revokeProcess(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "获取待办列表", response = FlowTaskDto.class)
    @GetMapping(value = "/todoList")
    public TableDataInfo<FlowTaskDto> todoList(PageQuery pageQuery) {
        return flowTaskService.todoList(pageQuery);
    }

    @ApiOperation(value = "获取已办任务", response = FlowTaskDto.class)
    @GetMapping(value = "/finishedList")
    public TableDataInfo<FlowTaskDto> finishedList(PageQuery pageQuery) {
        return flowTaskService.finishedList(pageQuery);
    }


    @ApiOperation(value = "流程历史流转记录", response = FlowTaskDto.class)
    @GetMapping(value = "/flowRecord")
    public R flowRecord(String procInsId, String deployId) {
        return R.ok(flowTaskService.flowRecord(procInsId, deployId));
    }

    @ApiOperation(value = "获取流程变量", response = FlowTaskDto.class)
    @GetMapping(value = "/processVariables/{taskId}")
    public R processVariables(@ApiParam(value = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @ApiOperation(value = "审批任务")
    @PostMapping(value = "/complete")
    public R complete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.complete(flowTaskVo);
        return R.ok();
    }


    @ApiOperation(value = "驳回任务")
    @PostMapping(value = "/reject")
    public R taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "退回任务")
    @PostMapping(value = "/return")
    public R taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReturn(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    public R findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
        return R.ok(flowTaskService.findReturnTaskList(flowTaskVo));
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping(value = "/delete")
    public R delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "认领/签收任务")
    @PostMapping(value = "/claim")
    public R claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    public R unClaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unClaim(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    public R delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "转办任务")
    @PostMapping(value = "/assign")
    public R assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
    public R getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo);
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
