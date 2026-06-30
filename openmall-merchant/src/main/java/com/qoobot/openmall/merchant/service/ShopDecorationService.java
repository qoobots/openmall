package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.common.domain.entity.ShopPageModule;

import java.util.List;

/**
 * 店铺装修服务
 */
public interface ShopDecorationService {

    List<ShopPageModule> getModules(Long shopId);

    ShopPageModule getById(Long id, Long shopId);

    Long save(ShopPageModule entity, Long shopId);

    void update(ShopPageModule entity, Long shopId);

    void delete(Long id, Long shopId);

    void updateSortOrder(Long id, Integer sortOrder, Long shopId);
}
