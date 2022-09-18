package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.workflow.domain.bo.WfCopyBo;
import com.ruoyi.workflow.domain.bo.WfProcessBo;
import com.ruoyi.workflow.domain.vo.WfCopyVo;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import com.ruoyi.workflow.service.IWfCopyService;
import com.ruoyi.workflow.service.IWfProcessService;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/process")
public class WfProcessController extends BaseController {

    private final IWfProcessService processService;
    private final IWfCopyService copyService;

    /**
     * 查询可发起流程列表
     *
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/list")
    @SaCheckPermission("workflow:process:startList")
    public TableDataInfo<WfDefinitionVo> list(PageQuery pageQuery) {
        return processService.processList(pageQuery);
    }

    /**
     * 查询流程部署关联表单信息
     *
     * @param definitionId 流程定义id
     * @param deployId 流程部署id
     */
    @GetMapping("/getProcessForm")
    @SaCheckPermission("workflow:process:start")
    public R<?> getForm(@RequestParam(value = "definitionId") String definitionId,
                        @RequestParam(value = "deployId") String deployId) {
        String formContent = processService.selectFormContent(definitionId, deployId);
        return R.ok(JsonUtils.parseObject(formContent, Map.class));
    }

    /**
     * 根据流程定义id启动流程实例
     *
     * @param processDefId 流程定义id
     * @param variables 变量集合,json对象
     */
    @SaCheckPermission("workflow:process:start")
    @PostMapping("/start/{processDefId}")
    public R<Void> start(@PathVariable(value = "processDefId") String processDefId, @RequestBody Map<String, Object> variables) {
        processService.startProcess(processDefId, variables);
        return R.ok("流程启动成功");

    }

    /**
     * 查询流程详情信息
     *
     * @param procInsId 流程实例ID
     * @param deployId 部署ID
     * @param taskId 任务ID
     */
    @GetMapping("/detail")
    public R detail(String procInsId, String deployId, String taskId) {
        return R.ok(processService.queryProcessDetail(procInsId, deployId, taskId));
    }

    /**
     * 我拥有的流程
     *
     * @param pageQuery 分页参数
     */
    @SaCheckPermission("workflow:process:ownList")
    @GetMapping(value = "/ownList")
    public TableDataInfo<WfTaskVo> ownProcess(PageQuery pageQuery) {
        return processService.queryPageOwnProcessList(pageQuery);
    }

    /**
     * 获取待办列表
     *
     * @param pageQuery 分页参数
     */
    @SaCheckPermission("workflow:process:todoList")
    @GetMapping(value = "/todoList")
    public TableDataInfo<WfTaskVo> todoProcess(PageQuery pageQuery) {
        return processService.queryPageTodoProcessList(pageQuery);
    }

    /**
     * 获取待签列表
     *
     * @param processBo 流程业务对象
     * @param pageQuery 分页参数
     */
    @SaCheckPermission("workflow:process:claimList")
    @GetMapping(value = "/claimList")
    public TableDataInfo<WfTaskVo> claimProcess(WfProcessBo processBo, PageQuery pageQuery) {
        return processService.queryPageClaimProcessList(processBo, pageQuery);
    }

    /**
     * 获取已办列表
     *
     * @param pageQuery 分页参数
     */
    @SaCheckPermission("workflow:process:finishedList")
    @GetMapping(value = "/finishedList")
    public TableDataInfo<WfTaskVo> finishedProcess(PageQuery pageQuery) {
        return processService.queryPageFinishedProcessList(pageQuery);
    }

    /**
     * 获取抄送列表
     *
     * @param copyBo 流程抄送对象
     * @param pageQuery 分页参数
     */
    @SaCheckPermission("workflow:process:copyList")
    @GetMapping(value = "/copyList")
    public TableDataInfo<WfCopyVo> copyProcess(WfCopyBo copyBo, PageQuery pageQuery) {
        copyBo.setUserId(getUserId());
        return copyService.queryPageList(copyBo, pageQuery);
    }
}
