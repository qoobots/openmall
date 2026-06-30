package com.qoobot.openmall.platform.repository;

import com.qoobot.openmall.common.domain.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 系统配置 Repository
 */
@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {

    Optional<SysConfig> findByConfigKeyAndIsDeleted(String configKey, Integer isDeleted);
}
