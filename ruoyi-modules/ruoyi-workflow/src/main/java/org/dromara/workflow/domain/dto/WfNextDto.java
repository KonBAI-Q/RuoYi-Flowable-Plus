package org.dromara.workflow.domain.dto;

import lombok.Data;
import org.dromara.system.domain.SysRole;
import org.dromara.system.domain.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * 动态人员、组
 * @author KonBAI
 * @createTime 2022/3/10 00:12
 */
@Data
public class WfNextDto implements Serializable {

    private String type;

    private String vars;

    private List<SysUser> userList;

    private List<SysRole> roleList;
}
