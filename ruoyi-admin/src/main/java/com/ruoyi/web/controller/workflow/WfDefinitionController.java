package com.ruoyi.web.controller.workflow;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.workflow.domain.bo.WfDesignerBo;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;
import com.ruoyi.workflow.service.IWfDefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 工作流程定义
 *
 * @author KonBAI
 * @date 2022-01-17
 */
@Slf4j
@Api(tags = "流程定义")
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/definition")
public class WfDefinitionController extends BaseController {

    private final IWfDefinitionService flowDefinitionService;

    @GetMapping(value = "/list")
    @SaCheckPermission("workflow:definition:list")
    @ApiOperation(value = "流程定义列表", response = WfDefinitionVo.class)
    public TableDataInfo<WfDefinitionVo> list(PageQuery pageQuery) {
        return flowDefinitionService.list(pageQuery);
    }

    /**
     * 列出指定流程的发布版本列表
     *
     * @param processKey 流程定义Key
     * @return
     */
    @GetMapping(value = "/publishList")
    @SaCheckPermission("workflow:definition:list")
    @ApiOperation(value = "指定流程的发布版本列表", response = WfDefinitionVo.class)
    public TableDataInfo<WfDefinitionVo> publishList(@ApiParam(value = "流程定义Key", required = true) @RequestParam String processKey,
                                                     PageQuery pageQuery) {
        return flowDefinitionService.publishList(processKey, pageQuery);
    }


    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
    @SaCheckPermission("workflow:definition:designer")
    @PostMapping("/import")
    public R<Void> importFile(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String category,
                              MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return R.fail(e.getMessage());
        }

        return R.ok("导入成功");
    }


    @ApiOperation(value = "读取xml文件")
    @SaCheckLogin
    @GetMapping("/readXml/{definitionId}")
    public R<String> readXml(@ApiParam(value = "流程定义ID") @PathVariable(value = "definitionId") String definitionId) {
        try {
            return R.ok(null, flowDefinitionService.readXml(definitionId));
        } catch (Exception e) {
            return R.fail("加载xml文件异常");
        }

    }

    @ApiOperation(value = "读取图片文件")
    @SaCheckPermission("workflow:definition:view")
    @GetMapping("/readImage/{definitionId}")
    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "definitionId") String definitionId,
                          HttpServletResponse response) {
        try (OutputStream os = response.getOutputStream()) {
            BufferedImage image = ImageIO.read(flowDefinitionService.readImage(definitionId));
            response.setContentType("image/png");
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "保存流程设计器内的xml文件")
    @SaCheckPermission("workflow:definition:designer")
    @PostMapping("/save")
    public R<Void> save(@RequestBody WfDesignerBo bo) {
        try (InputStream in = new ByteArrayInputStream(bo.getXml().getBytes(StandardCharsets.UTF_8))) {
            flowDefinitionService.importFile(bo.getName(), bo.getCategory(), in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return R.ok(e.getMessage());
        }

        return R.ok("导入成功");
    }

    @ApiOperation(value = "激活或挂起流程定义")
    @SaCheckPermission("workflow:definition:update")
    @PutMapping(value = "/updateState")
    public R<Void> updateState(@ApiParam(value = "ture:挂起,false:激活", required = true) @RequestParam Boolean suspended,
                               @ApiParam(value = "流程定义ID", required = true) @RequestParam String definitionId) {
        flowDefinitionService.updateState(suspended, definitionId);
        return R.ok();
    }

    @ApiOperation(value = "删除流程")
    @SaCheckPermission("workflow:definition:remove")
    @DeleteMapping(value = "/delete")
    public R<Void> delete(@ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return R.ok();
    }

}
