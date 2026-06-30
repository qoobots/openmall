package com.qoobot.openmall.platform.service;

import com.qoobot.openmall.common.domain.entity.SysRole;
import java.util.List;

/**
 * 系统角色服务
 */
public interface SysRoleService {

    List<SysRole> listAll();

    SysRole getById(Long id);

    SysRole save(SysRole role);

    SysRole update(SysRole role);

    void delete(Long id);

    void assignMenus(Long roleId, List<Long> menuIds);
}
