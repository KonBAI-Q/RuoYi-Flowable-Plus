package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.workflow.domain.bo.WfProcessBo;
import com.ruoyi.workflow.domain.vo.WfDeployVo;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.service.IWfDeployFormService;
import com.ruoyi.workflow.service.IWfDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author KonBAI
 * @createTime 2022/3/24 20:57
 */
@Slf4j
@Api(tags = "流程部署")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/deploy")
public class WfDeployController extends BaseController {

    private final IWfDeployService deployService;
    private final IWfDeployFormService deployFormService;

    /**
     * 查询流程部署列表
     */
    @SaCheckPermission("workflow:deploy:list")
    @GetMapping("/list")
    public TableDataInfo<WfDeployVo> list(WfProcessBo processBo, PageQuery pageQuery) {
        return deployService.queryPageList(processBo, pageQuery);
    }

    /**
     * 查询流程部署版本列表
     */
    @SaCheckPermission("workflow:deploy:list")
    @GetMapping("/publishList")
    public TableDataInfo<WfDeployVo> publishList(@ApiParam(value = "流程定义Key", required = true) @RequestParam String processKey,
                                                 PageQuery pageQuery) {
        return deployService.queryPublishList(processKey, pageQuery);
    }

    /**
     *
     * @param state
     * @param definitionId
     * @return
     */
    @ApiOperation(value = "激活或挂起流程")
    @SaCheckPermission("workflow:deploy:state")
    @PutMapping(value = "/changeState")
    public R<Void> changeState(@ApiParam(value = "状态（active:激活 suspended:挂起）", required = true) @RequestParam String state,
                               @ApiParam(value = "流程定义ID", required = true) @RequestParam String definitionId) {
        deployService.updateState(definitionId, state);
        return R.ok();
    }

    @ApiOperation(value = "读取xml文件")
    @SaCheckPermission("workflow:deploy:query")
    @GetMapping("/bpmnXml/{definitionId}")
    public R<String> getBpmnXml(@ApiParam(value = "流程定义ID") @PathVariable(value = "definitionId") String definitionId) {
        return R.ok(null, deployService.queryBpmnXmlById(definitionId));
    }

    /**
     *
     * @param deployId
     * @return
     */
    @ApiOperation(value = "查询流程部署关联表单信息")
    @GetMapping("/form/{deployId}")
    public R<?> start(@ApiParam(value = "流程部署id") @PathVariable(value = "deployId") String deployId) {
        WfFormVo formVo = deployFormService.selectDeployFormByDeployId(deployId);
        if (Objects.isNull(formVo)) {
            return R.fail("请先配置流程表单");
        }
        return R.ok(JsonUtils.parseObject(formVo.getContent(), Map.class));
    }
}
