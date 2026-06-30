package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 商家端评价管理控制器
 */
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 回复评价
     */
    @PostMapping("/reply/{id}")
    @ResponseBody
    public Result<Void> reply(@PathVariable Long id, @RequestParam String replyContent) {
        try {
            reviewService.replyReview(id, replyContent, getShopId());
            return Result.success("回复成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private Long getShopId() {
        return 1L;
    }
}
