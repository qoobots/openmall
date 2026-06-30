package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.ShopPageModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopPageModuleRepository extends JpaRepository<ShopPageModule, Long> {
    List<ShopPageModule> findByShopIdAndIsEnabledAndIsDeletedOrderBySortOrderAsc(Long shopId, Integer isEnabled, Integer isDeleted);
}
