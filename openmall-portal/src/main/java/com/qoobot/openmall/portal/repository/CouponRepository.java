package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券 Repository (Portal)
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * 查询可领取的优惠券（在有效期内、有剩余数量、已启用）
     */
    Page<Coupon> findByStatusAndIsDeleted(Integer status, Integer isDeleted, Pageable pageable);

    /**
     * 根据店铺ID查询
     */
    List<Coupon> findByShopIdAndStatusAndIsDeleted(Long shopId, Integer status, Integer isDeleted);
}
