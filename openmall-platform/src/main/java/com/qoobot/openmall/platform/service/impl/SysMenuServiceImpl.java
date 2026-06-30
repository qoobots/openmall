package com.qoobot.openmall.platform.service.impl;

import com.qoobot.openmall.common.domain.entity.SysMenu;
import com.qoobot.openmall.platform.repository.SysMenuRepository;
import com.qoobot.openmall.platform.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuRepository sysMenuRepository;

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> allMenus = sysMenuRepository.findByIsDeletedOrderBySortOrderAsc(0);
        Map<Long, List<SysMenu>> parentMap = allMenus.stream()
                .collect(Collectors.groupingBy(m -> m.getParentId() != null ? m.getParentId() : 0L));

        List<SysMenu> rootMenus = parentMap.getOrDefault(0L, new ArrayList<>());
        for (SysMenu root : rootMenus) {
            buildChildren(root, parentMap);
        }
        return rootMenus;
    }

    private void buildChildren(SysMenu parent, Map<Long, List<SysMenu>> parentMap) {
        List<SysMenu> children = parentMap.getOrDefault(parent.getId(), new ArrayList<>());
        parent.setChildren(children);
        for (SysMenu child : children) {
            buildChildren(child, parentMap);
        }
    }

    @Override
    public SysMenu getById(Long id) {
        return sysMenuRepository.findById(id).orElseThrow(() -> new RuntimeException("菜单不存在"));
    }

    @Override
    @Transactional
    public SysMenu save(SysMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        return sysMenuRepository.save(menu);
    }

    @Override
    @Transactional
    public SysMenu update(SysMenu menu) {
        SysMenu existing = getById(menu.getId());
        existing.setMenuName(menu.getMenuName());
        existing.setMenuPath(menu.getMenuPath());
        existing.setMenuIcon(menu.getMenuIcon());
        existing.setPermission(menu.getPermission());
        existing.setMenuType(menu.getMenuType());
        existing.setSortOrder(menu.getSortOrder());
        existing.setIsVisible(menu.getIsVisible());
        existing.setParentId(menu.getParentId());
        existing.setUpdateTime(LocalDateTime.now());
        return sysMenuRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysMenu menu = getById(id);
        menu.setIsDeleted(1);
        menu.setUpdateTime(LocalDateTime.now());
        sysMenuRepository.save(menu);
    }
}
