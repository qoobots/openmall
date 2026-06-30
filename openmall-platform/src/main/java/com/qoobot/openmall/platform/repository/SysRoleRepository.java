package com.qoobot.openmall.platform.repository;

import com.qoobot.openmall.common.domain.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 系统角色 Repository
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {

    Optional<SysRole> findByRoleCodeAndIsDeleted(String roleCode, Integer isDeleted);

    boolean existsByRoleCodeAndIsDeleted(String roleCode, Integer isDeleted);
}
