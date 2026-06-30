package com.qoobot.openmall.platform.service.impl;

import com.qoobot.openmall.common.domain.entity.SysRole;
import com.qoobot.openmall.common.domain.entity.SysRoleMenu;
import com.qoobot.openmall.platform.repository.SysRoleRepository;
import com.qoobot.openmall.platform.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色服务实现
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;
    private final JpaRepository<SysRoleMenu, Long> sysRoleMenuRepository;

    @Override
    public List<SysRole> listAll() {
        return sysRoleRepository.findAll();
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("角色不存在"));
    }

    @Override
    @Transactional
    public SysRole save(SysRole role) {
        if (sysRoleRepository.existsByRoleCodeAndIsDeleted(role.getRoleCode(), 0)) {
            throw new RuntimeException("角色编码已存在");
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return sysRoleRepository.save(role);
    }

    @Override
    @Transactional
    public SysRole update(SysRole role) {
        SysRole existing = getById(role.getId());
        existing.setRoleName(role.getRoleName());
        existing.setRoleDesc(role.getRoleDesc());
        existing.setStatus(role.getStatus());
        existing.setUpdateTime(LocalDateTime.now());
        return sysRoleRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysRole role = getById(id);
        role.setIsDeleted(1);
        role.setUpdateTime(LocalDateTime.now());
        sysRoleRepository.save(role);
    }

    @Override
    @Transactional
    public void assignMenus(Long roleId, List<Long> menuIds) {
        // 删除旧关联
        sysRoleMenuRepository.findAll().stream()
                .filter(rm -> rm.getRoleId().equals(roleId))
                .forEach(rm -> {
                    rm.setIsDeleted(1);
                    sysRoleMenuRepository.save(rm);
                });

        // 创建新关联
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setCreateTime(LocalDateTime.now());
            roleMenu.setUpdateTime(LocalDateTime.now());
            roleMenu.setCreateBy(1L);
            roleMenu.setUpdateBy(1L);
            sysRoleMenuRepository.save(roleMenu);
        }
    }
}
