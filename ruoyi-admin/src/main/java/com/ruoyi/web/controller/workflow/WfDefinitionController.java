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
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/definition")
@Deprecated
public class WfDefinitionController extends BaseController {

    private final IWfDefinitionService flowDefinitionService;

    /**
     * 流程定义列表
     *
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/list")
    @SaCheckPermission("workflow:definition:list")
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
    public TableDataInfo<WfDefinitionVo> publishList(@RequestParam String processKey, PageQuery pageQuery) {
        return flowDefinitionService.publishList(processKey, pageQuery);
    }

    /**
     * 导入流程文件
     */
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

    /**
     * 读取xml文件
     */
    @SaCheckLogin
    @GetMapping("/readXml/{definitionId}")
    public R<String> readXml(@PathVariable(value = "definitionId") String definitionId) {
        try {
            return R.ok(null, flowDefinitionService.readXml(definitionId));
        } catch (Exception e) {
            return R.fail("加载xml文件异常");
        }

    }

    /**
     * 读取图片文件
     */
    @SaCheckPermission("workflow:definition:view")
    @GetMapping("/readImage/{definitionId}")
    public void readImage(@PathVariable(value = "definitionId") String definitionId,
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

    /**
     * 保存流程设计器内的xml文件
     */
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

    /**
     * 激活或挂起流程定义
     */
    @SaCheckPermission("workflow:definition:update")
    @PutMapping(value = "/updateState")
    public R<Void> updateState(@RequestParam Boolean suspended, @RequestParam String definitionId) {
        flowDefinitionService.updateState(suspended, definitionId);
        return R.ok();
    }

    /**
     * 删除流程
     */
    @SaCheckPermission("workflow:definition:remove")
    @DeleteMapping(value = "/delete")
    public R<Void> delete(@RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return R.ok();
    }

}
