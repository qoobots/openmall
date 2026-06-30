package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.Coupon;
import com.qoobot.openmall.common.domain.entity.UserCoupon;

import java.util.List;

/**
 * 优惠券服务接口
 */
public interface CouponService {

    /**
     * 分页查询可领取的优惠券
     */
    PageResult<Coupon> listAvailableCoupons(int pageNum, int pageSize);

    /**
     * 用户领取优惠券
     */
    UserCoupon receiveCoupon(Long userId, Long couponId);

    /**
     * 查询用户优惠券列表
     */
    PageResult<UserCoupon> listUserCoupons(Long userId, Integer status, int pageNum, int pageSize);

    /**
     * 查询用户可用优惠券（下单时选择）
     */
    List<UserCoupon> getAvailableCoupons(Long userId);

    /**
     * 使用优惠券（下单时核销）
     */
    void useCoupon(Long userCouponId, String orderNo);
}
