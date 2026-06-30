package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.Coupon;
import com.qoobot.openmall.common.domain.entity.UserCoupon;
import com.qoobot.openmall.portal.service.CouponService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券控制器
 */
@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * 优惠券中心页面
     */
    @GetMapping("/center")
    public String center(@RequestParam(defaultValue = "1") int pageNum,
                         HttpSession session, Model model) {
        Long userId = getUserId(session);
        PageResult<Coupon> availableCoupons = couponService.listAvailableCoupons(pageNum, 10);
        PageResult<UserCoupon> myCoupons = couponService.listUserCoupons(userId, null, 1, 10);
        model.addAttribute("availableCoupons", availableCoupons);
        model.addAttribute("myCoupons", myCoupons);
        return "coupon/center";
    }

    /**
     * 可领优惠券列表
     */
    @GetMapping("/available")
    @ResponseBody
    public Result<PageResult<Coupon>> listAvailable(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(couponService.listAvailableCoupons(pageNum, pageSize));
    }

    /**
     * 领取优惠券
     */
    @PostMapping("/receive/{couponId}")
    @ResponseBody
    public Result<UserCoupon> receive(@PathVariable Long couponId, HttpSession session) {
        Long userId = getUserId(session);
        return Result.success(couponService.receiveCoupon(userId, couponId));
    }

    /**
     * 我的优惠券
     */
    @GetMapping("/my")
    @ResponseBody
    public Result<PageResult<UserCoupon>> myCoupons(@RequestParam(required = false) Integer status,
                                                     @RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize,
                                                     HttpSession session) {
        Long userId = getUserId(session);
        return Result.success(couponService.listUserCoupons(userId, status, pageNum, pageSize));
    }

    /**
     * 获取下单时可用的优惠券
     */
    @GetMapping("/usable")
    @ResponseBody
    public Result<List<UserCoupon>> getUsableCoupons(HttpSession session) {
        Long userId = getUserId(session);
        return Result.success(couponService.getAvailableCoupons(userId));
    }

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L; // 测试默认
        }
        return userId;
    }
}
