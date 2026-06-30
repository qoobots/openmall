package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.merchant.dto.ShopDTO;

/**
 * 店铺Service
 */
public interface ShopService {

    /**
     * 获取店铺信息
     */
    ShopDTO getByMerchantId(Long merchantId);

    /**
     * 保存店铺信息
     */
    void save(ShopDTO dto, Long merchantId);

    /**
     * 更新店铺信息
     */
    void update(ShopDTO dto, Long merchantId);

    /**
     * 检查店铺是否存在
     */
    boolean existsByMerchantId(Long merchantId);
}
