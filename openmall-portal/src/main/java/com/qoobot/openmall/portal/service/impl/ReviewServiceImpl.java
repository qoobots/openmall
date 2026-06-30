package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.OrderReview;
import com.qoobot.openmall.portal.repository.OrderReviewRepository;
import com.qoobot.openmall.portal.service.ReviewService;
import com.qoobot.openmall.portal.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderReviewRepository reviewRepository;

    @Override
    public PageResult<ReviewVO> getProductReviews(Long spuId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<OrderReview> page = reviewRepository.findBySpuIdAndIsDeletedOrderByCreateTimeDesc(spuId, 0, pageable);
        return new PageResult<>(
                page.getContent().stream().map(this::convertToVO).collect(Collectors.toList()),
                page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitReview(Long userId, String orderNo, Long spuId, Long skuId, Integer rating, String content, String images, Integer isAnonymous) {
        // 检查是否已评价
        if (reviewRepository.existsByOrderNoAndSpuIdAndIsDeleted(orderNo, spuId, 0)) {
            throw new RuntimeException("该商品已评价");
        }

        OrderReview review = new OrderReview();
        review.setOrderNo(orderNo);
        review.setUserId(userId);
        review.setSpuId(spuId);
        review.setSkuId(skuId);
        review.setRating(rating);
        review.setContent(content);
        review.setImages(images);
        review.setIsAnonymous(isAnonymous != null ? isAnonymous : 0);
        review.setIsDeleted(0);
        review.setVersion(0);
        review.setCreateTime(LocalDateTime.now());
        review.setUpdateTime(LocalDateTime.now());
        review.setCreateBy(userId);
        review.setUpdateBy(userId);
        reviewRepository.save(review);
    }

    @Override
    public PageResult<ReviewVO> getUserReviews(Long userId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<OrderReview> page = reviewRepository.findByUserIdAndIsDeletedOrderByCreateTimeDesc(userId, 0, pageable);
        return new PageResult<>(
                page.getContent().stream().map(this::convertToVO).collect(Collectors.toList()),
                page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyReview(Long reviewId, String replyContent, Long shopId) {
        OrderReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("评价不存在"));
        review.setReplyContent(replyContent);
        review.setReplyTime(LocalDateTime.now());
        review.setUpdateTime(LocalDateTime.now());
        review.setUpdateBy(shopId);
        reviewRepository.save(review);
    }

    private ReviewVO convertToVO(OrderReview review) {
        ReviewVO vo = new ReviewVO();
        vo.setId(review.getId());
        vo.setOrderNo(review.getOrderNo());
        vo.setUserId(review.getUserId());
        vo.setSpuId(review.getSpuId());
        vo.setSkuId(review.getSkuId());
        vo.setRating(review.getRating());
        vo.setContent(review.getContent());
        vo.setImages(review.getImages());
        vo.setIsAnonymous(review.getIsAnonymous());
        vo.setReplyContent(review.getReplyContent());
        vo.setReplyTime(review.getReplyTime());
        vo.setCreateTime(review.getCreateTime());
        return vo;
    }
}
