package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.portal.vo.ReviewVO;

/**
 * 评价服务
 */
public interface ReviewService {

    /**
     * 获取商品评价列表
     */
    PageResult<ReviewVO> getProductReviews(Long spuId, Integer pageNum, Integer pageSize);

    /**
     * 提交评价
     */
    void submitReview(Long userId, String orderNo, Long spuId, Long skuId, Integer rating, String content, String images, Integer isAnonymous);

    /**
     * 获取用户评价列表
     */
    PageResult<ReviewVO> getUserReviews(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 商家回复评价
     */
    void replyReview(Long reviewId, String replyContent, Long shopId);
}
