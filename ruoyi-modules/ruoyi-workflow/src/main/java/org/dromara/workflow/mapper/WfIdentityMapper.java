package org.dromara.workflow.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;

import java.util.List;
import java.util.Map;

/**
 * 身份Mapper接口
 *
 * @author KonBAI
 */
public interface WfIdentityMapper {

    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id"),
            @DataColumn(key = "userName", value = "u.user_id")
    })
    Page<Map<String, Object>> selectPageUserList(@Param("page") Page<?> page, @Param("deptId") Long deptId);

    @DataPermission({
            @DataColumn(key = "deptName", value = "d.dept_id")
    })
    List<Map<String, Object>> selectDeptList();
}

