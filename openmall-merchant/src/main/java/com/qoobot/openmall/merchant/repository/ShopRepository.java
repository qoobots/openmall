package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 店铺Repository
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    /**
     * 根据商家ID查询店铺
     */
    Optional<Shop> findByMerchantId(Long merchantId);
    
    /**
     * 根据商家ID统计店铺数量
     */
    long countByMerchantId(Long merchantId);
    
    /**
     * 检查商家是否存在店铺
     */
    boolean existsByMerchantId(Long merchantId);
}
