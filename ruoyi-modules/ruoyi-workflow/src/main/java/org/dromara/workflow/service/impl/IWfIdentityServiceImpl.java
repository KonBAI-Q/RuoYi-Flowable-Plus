package org.dromara.workflow.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.TreeBuildUtils;
import org.dromara.common.core.utils.reflect.ReflectUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.workflow.mapper.WfIdentityMapper;
import org.dromara.workflow.service.IWfIdentityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务实现
 *
 * @author KonBAI
 */
@RequiredArgsConstructor
@Service
public class IWfIdentityServiceImpl implements IWfIdentityService {

    private final WfIdentityMapper baseMapper;

    /**
     * 查询部门树结构信息
     *
     * @return 部门树信息集合
     */
    @Override
    public List<Tree<Long>> selectDeptTreeList() {
        List<Map<String, Object>> list = baseMapper.selectDeptList();
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return treeBuild(list, (dept, tree) ->
                tree.setId(Convert.toLong(dept.get("deptId")))
                    .setParentId(Convert.toLong(dept.get("parentId")))
                    .setName(Convert.toStr(dept.get("deptName")))
                    .setWeight(Convert.toInt(dept.get("orderNum"))));
    }

    @Override
    public TableDataInfo<Map<String, Object>> selectPageUserList(Long deptId, PageQuery pageQuery) {
        return TableDataInfo.build(baseMapper.selectPageUserList(pageQuery.build(), deptId));
    }

    /**
     * 树构建
     */
    private static <T, K> List<Tree<K>> treeBuild(List<T> list, NodeParser<T, K> nodeParser) {
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        T t = list.get(0);
        K k = (t instanceof Map<?, ?> map) ? (K) map.get("parentId") : ReflectUtils.invokeGetter(t, "parentId");
        return TreeUtil.build(list, k, TreeBuildUtils.DEFAULT_CONFIG, nodeParser);
    }
}
