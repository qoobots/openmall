package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.service.ReviewService;
import com.qoobot.openmall.portal.vo.ReviewVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 评价控制器
 */
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 商品评价页
     */
    @GetMapping("/review/product/{spuId}")
    public String productReviews(@PathVariable Long spuId,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  Model model) {
        PageResult<ReviewVO> page = reviewService.getProductReviews(spuId, pageNum, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("spuId", spuId);
        return "review/product";
    }

    /**
     * 提交评价页
     */
    @GetMapping("/review/submit")
    public String submitPage(@RequestParam String orderNo,
                              @RequestParam Long spuId,
                              @RequestParam(required = false) Long skuId,
                              Model model) {
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("spuId", spuId);
        model.addAttribute("skuId", skuId);
        return "review/submit";
    }

    /**
     * 提交评价
     */
    @PostMapping("/review/submit")
    @ResponseBody
    public Result<Void> submit(@RequestParam String orderNo,
                                @RequestParam Long spuId,
                                @RequestParam(required = false) Long skuId,
                                @RequestParam Integer rating,
                                @RequestParam(required = false) String content,
                                @RequestParam(required = false) String images,
                                @RequestParam(defaultValue = "0") Integer isAnonymous,
                                HttpSession session) {
        Long userId = getUserId(session);
        try {
            reviewService.submitReview(userId, orderNo, spuId, skuId, rating, content, images, isAnonymous);
            return Result.success("评价成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 我的评价
     */
    @GetMapping("/review/my")
    public String myReviews(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             HttpSession session, Model model) {
        Long userId = getUserId(session);
        PageResult<ReviewVO> page = reviewService.getUserReviews(userId, pageNum, pageSize);
        model.addAttribute("page", page);
        return "review/my";
    }

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userId != null ? userId : 1L;
    }
}
