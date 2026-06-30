package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.ShopPageModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺页面模块 Repository
 */
@Repository
public interface ShopPageModuleRepository extends JpaRepository<ShopPageModule, Long> {

    List<ShopPageModule> findByShopIdAndIsDeletedOrderBySortOrderAsc(Long shopId, Integer isDeleted);

    List<ShopPageModule> findByShopIdAndIsEnabledAndIsDeletedOrderBySortOrderAsc(Long shopId, Integer isEnabled, Integer isDeleted);
}
