package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.Coupon;
import com.qoobot.openmall.common.domain.entity.UserCoupon;
import com.qoobot.openmall.portal.repository.CouponRepository;
import com.qoobot.openmall.portal.repository.UserCouponRepository;
import com.qoobot.openmall.portal.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠券服务实现
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    public PageResult<Coupon> listAvailableCoupons(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Coupon> page = couponRepository.findByStatusAndIsDeleted(1, 0, pageable);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    @Transactional
    public UserCoupon receiveCoupon(Long userId, Long couponId) {
        // 检查优惠券是否存在且有效
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        if (coupon.getStatus() != 1) {
            throw new RuntimeException("优惠券已失效");
        }

        if (coupon.getRemainCount() != null && coupon.getRemainCount() <= 0) {
            throw new RuntimeException("优惠券已领完");
        }

        if (coupon.getEndTime() != null && coupon.getEndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("优惠券已过期");
        }

        // 检查是否已领取
        if (userCouponRepository.findByUserIdAndCouponIdAndIsDeleted(userId, couponId, 0).isPresent()) {
            throw new RuntimeException("已领取过该优惠券");
        }

        // 创建用户优惠券记录
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus(0); // 未使用
        userCoupon.setCreateTime(LocalDateTime.now());
        userCoupon.setUpdateTime(LocalDateTime.now());
        userCoupon.setCreateBy(userId);
        userCoupon.setUpdateBy(userId);

        // 扣减优惠券剩余数量
        if (coupon.getRemainCount() != null && coupon.getRemainCount() > 0) {
            coupon.setRemainCount(coupon.getRemainCount() - 1);
            couponRepository.save(coupon);
        }

        return userCouponRepository.save(userCoupon);
    }

    @Override
    public PageResult<UserCoupon> listUserCoupons(Long userId, Integer status, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<UserCoupon> page;
        if (status != null) {
            page = userCouponRepository.findByUserIdAndStatusAndIsDeleted(userId, status, 0, pageable);
        } else {
            page = userCouponRepository.findByUserIdAndIsDeletedOrderByCreateTimeDesc(userId, 0, pageable);
        }
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    public List<UserCoupon> getAvailableCoupons(Long userId) {
        return userCouponRepository.findAvailableByUserId(userId);
    }

    @Override
    @Transactional
    public void useCoupon(Long userCouponId, String orderNo) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        if (userCoupon.getStatus() != 0) {
            throw new RuntimeException("优惠券已使用或已过期");
        }

        userCoupon.setStatus(1); // 已使用
        userCoupon.setUseTime(LocalDateTime.now());
        userCoupon.setOrderNo(orderNo);
        userCoupon.setUpdateTime(LocalDateTime.now());
        userCouponRepository.save(userCoupon);
    }
}
