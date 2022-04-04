package com.ruoyi.workflow.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.workflow.domain.vo.WfDefinitionVo;
import com.ruoyi.workflow.domain.vo.WfFormVo;
import com.ruoyi.workflow.service.IWfDefinitionService;
import com.ruoyi.workflow.service.IWfDeployFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 流程定义
 *
 * @author KonBAI
 * @date 2022-01-17
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class WfDefinitionServiceImpl extends FlowServiceFactory implements IWfDefinitionService {

    private final IWfDeployFormService deployFormService;

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0;
    }


    /**
     * 流程定义列表
     *
     * @param pageQuery 分页参数
     * @return 流程定义分页列表数据
     */
    @Override
    public TableDataInfo<WfDefinitionVo> list(PageQuery pageQuery) {
        Page<WfDefinitionVo> page = new Page<>();
        // 流程定义列表数据查询
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .latestVersion()
            .orderByProcessDefinitionKey().asc();
        long pageTotal = processDefinitionQuery.count();
        if (pageTotal <= 0) {
            return TableDataInfo.build();
        }
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<ProcessDefinition> definitionList = processDefinitionQuery.listPage(offset, pageQuery.getPageSize());

        List<WfDefinitionVo> definitionVoList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            WfDefinitionVo vo = new WfDefinitionVo();
            vo.setDefinitionId(processDefinition.getId());
            vo.setProcessKey(processDefinition.getKey());
            vo.setProcessName(processDefinition.getName());
            vo.setVersion(processDefinition.getVersion());
            vo.setCategory(processDefinition.getCategory());
            vo.setDeploymentId(processDefinition.getDeploymentId());
            vo.setSuspended(processDefinition.isSuspended());
            WfFormVo formVo = deployFormService.selectDeployFormByDeployId(deploymentId);
            if (Objects.nonNull(formVo)) {
                vo.setFormId(formVo.getFormId());
                vo.setFormName(formVo.getFormName());
            }
            // 流程定义时间
            vo.setCategory(deployment.getCategory());
            vo.setDeploymentTime(deployment.getDeploymentTime());
            definitionVoList.add(vo);
        }
        page.setRecords(definitionVoList);
        page.setTotal(pageTotal);
        return TableDataInfo.build(page);
    }

    @Override
    public TableDataInfo<WfDefinitionVo> publishList(String processKey, PageQuery pageQuery) {
        Page<WfDefinitionVo> page = new Page<>();
        // 创建查询条件
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey(processKey)
            .orderByProcessDefinitionVersion().asc();
        long pageTotal = processDefinitionQuery.count();
        if (pageTotal <= 0) {
            return TableDataInfo.build();
        }
        // 根据查询条件，查询所有版本
        int offset = pageQuery.getPageSize() * (pageQuery.getPageNum() - 1);
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
            .listPage(offset, pageQuery.getPageSize());
        List<WfDefinitionVo> definitionVoList = processDefinitionList.stream().map(item -> {
            WfDefinitionVo vo = new WfDefinitionVo();
            vo.setDefinitionId(item.getId());
            vo.setProcessKey(item.getKey());
            vo.setProcessName(item.getName());
            vo.setVersion(item.getVersion());
            vo.setCategory(item.getCategory());
            vo.setDeploymentId(item.getDeploymentId());
            vo.setSuspended(item.isSuspended());
            // BeanUtil.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        page.setRecords(definitionVoList);
        page.setTotal(pageTotal);
        return TableDataInfo.build(page);
    }


    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importFile(String name, String category, InputStream in) {
        String processName = name + BPMN_FILE_SUFFIX;
        // 创建流程部署
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
            .name(processName)
            .key(name)
            .category(category)
            .addInputStream(processName, in);
        // 部署
        deploymentBuilder.deploy();
    }

    /**
     * 读取xml
     *
     * @param definitionId 流程定义ID
     * @return
     */
    @Override
    public String readXml(String definitionId) throws IOException {
        InputStream inputStream = repositoryService.getProcessModel(definitionId);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    }

    /**
     * 读取xml
     *
     * @param definitionId 流程定义ID
     * @return
     */
    @Override
    public InputStream readImage(String definitionId) {
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
        //输出为图片
        return diagramGenerator.generateDiagram(
            bpmnModel,
            "png",
            Collections.emptyList(),
            Collections.emptyList(),
            "宋体",
            "宋体",
            "宋体",
            null,
            1.0,
            false);

    }


    /**
     * 激活或挂起流程定义
     *
     * @param suspended 是否暂停状态
     * @param definitionId 流程定义ID
     */
    @Override
    public void updateState(Boolean suspended, String definitionId) {
        if (!suspended) {
            // 激活
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
        } else {
            // 挂起
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        }
    }


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }


}
