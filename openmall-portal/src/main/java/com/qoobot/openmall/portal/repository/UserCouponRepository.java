package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.Coupon;
import com.qoobot.openmall.common.domain.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户优惠券 Repository (Portal)
 */
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Page<UserCoupon> findByUserIdAndIsDeletedOrderByCreateTimeDesc(Long userId, Integer isDeleted, Pageable pageable);

    Page<UserCoupon> findByUserIdAndStatusAndIsDeleted(Long userId, Integer status, Integer isDeleted, Pageable pageable);

    Optional<UserCoupon> findByUserIdAndCouponIdAndIsDeleted(Long userId, Long couponId, Integer isDeleted);

    long countByCouponIdAndIsDeleted(Long couponId, Integer isDeleted);

    /**
     * 查询用户可用优惠券（未使用、未过期）
     */
    @Query("SELECT uc FROM UserCoupon uc WHERE uc.userId = :userId AND uc.status = 0 AND uc.isDeleted = 0")
    List<UserCoupon> findAvailableByUserId(@Param("userId") Long userId);
}
