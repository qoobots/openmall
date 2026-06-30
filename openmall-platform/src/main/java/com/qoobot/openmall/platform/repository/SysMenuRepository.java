package com.qoobot.openmall.platform.repository;

import com.qoobot.openmall.common.domain.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单 Repository
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {

    List<SysMenu> findByParentIdAndIsDeletedOrderBySortOrderAsc(Long parentId, Integer isDeleted);

    List<SysMenu> findByIsDeletedOrderBySortOrderAsc(Integer isDeleted);

    @Query("SELECT m FROM SysMenu m WHERE m.id IN (SELECT rm.menuId FROM SysRoleMenu rm WHERE rm.roleId = :roleId AND rm.isDeleted = 0) AND m.isDeleted = 0 ORDER BY m.sortOrder ASC")
    List<SysMenu> findByRoleId(@Param("roleId") Long roleId);
}
