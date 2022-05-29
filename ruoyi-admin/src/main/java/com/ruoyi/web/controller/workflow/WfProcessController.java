package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.bo.WfCopyBo;
import com.ruoyi.workflow.domain.vo.WfCopyVo;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.service.IWfCopyService;
import com.ruoyi.workflow.service.IWfProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工作流流程管理
 *
 * @author KonBAI
 * @createTime 2022/3/24 18:54
 */
@Slf4j
@Api(tags = "工作流流程管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/process")
public class WfProcessController extends BaseController {

    private final IWfProcessService processService;
    private final IWfCopyService copyService;

    @GetMapping(value = "/list")
    @SaCheckPermission("workflow:process:startList")
    @ApiOperation(value = "查询可发起流程列表", response = WfDefinitionVo.class)
    public TableDataInfo<WfDefinitionVo> list(PageQuery pageQuery) {
        return processService.processList(pageQuery);
    }

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @SaCheckPermission("workflow:process:start")
    @PostMapping("/start/{processDefId}")
    public R<Void> start(@ApiParam(value = "流程定义id") @PathVariable(value = "processDefId") String processDefId,
                         @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
        processService.startProcess(processDefId, variables);
        return R.ok("流程启动成功");

    }

    @ApiOperation(value = "我拥有的流程", response = WfTaskVo.class)
    @SaCheckPermission("workflow:process:ownList")
    @GetMapping(value = "/ownList")
    public TableDataInfo<WfTaskVo> ownProcess(PageQuery pageQuery) {
        return processService.queryPageOwnProcessList(pageQuery);
    }

    @ApiOperation(value = "获取待办列表", response = WfTaskVo.class)
    @SaCheckPermission("workflow:process:todoList")
    @GetMapping(value = "/todoList")
    public TableDataInfo<WfTaskVo> todoProcess(PageQuery pageQuery) {
        return processService.queryPageTodoProcessList(pageQuery);
    }

    @ApiOperation(value = "获取已办列表", response = WfTaskVo.class)
    @SaCheckPermission("workflow:process:finishedList")
    @GetMapping(value = "/finishedList")
    public TableDataInfo<WfTaskVo> finishedProcess(PageQuery pageQuery) {
        return processService.queryPageFinishedProcessList(pageQuery);
    }

    @ApiOperation(value = "获取抄送列表", response = WfTaskVo.class)
    @SaCheckPermission("workflow:process:copyList")
    @GetMapping(value = "/copyList")
    public TableDataInfo<WfCopyVo> copyProcess(WfCopyBo copyBo, PageQuery pageQuery) {
        copyBo.setUserId(getUserId());
        return copyService.queryPageList(copyBo, pageQuery);
    }
}
