package com.qoobot.openmall.platform.service;

import com.qoobot.openmall.common.domain.entity.SysMenu;
import java.util.List;

/**
 * 系统菜单服务
 */
public interface SysMenuService {

    List<SysMenu> getMenuTree();

    SysMenu getById(Long id);

    SysMenu save(SysMenu menu);

    SysMenu update(SysMenu menu);

    void delete(Long id);
}
