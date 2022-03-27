package com.ruoyi.web.controller.workflow;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.service.IWfDeployFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final IWfDeployFormService deployFormService;

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
