package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户优惠券 Repository
 */
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    /**
     * 根据用户ID和状态查询
     */
    Page<UserCoupon> findByUserIdAndStatusAndIsDeleted(Long userId, Integer status, Integer isDeleted, Pageable pageable);

    /**
     * 根据用户ID查询所有未删除的优惠券
     */
    Page<UserCoupon> findByUserIdAndIsDeleted(Long userId, Integer isDeleted, Pageable pageable);

    /**
     * 查询用户是否已领取某优惠券
     */
    Optional<UserCoupon> findByUserIdAndCouponIdAndIsDeleted(Long userId, Long couponId, Integer isDeleted);

    /**
     * 统计优惠券领取数量
     */
    long countByCouponIdAndIsDeleted(Long couponId, Integer isDeleted);
}
