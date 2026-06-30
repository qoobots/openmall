package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 优惠券Repository
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    /**
     * 根据店铺ID分页查询优惠券
     */
    Page<Coupon> findByShopId(Long shopId, Pageable pageable);
    
    /**
     * 统计店铺优惠券数量
     */
    long countByShopId(Long shopId);
}
