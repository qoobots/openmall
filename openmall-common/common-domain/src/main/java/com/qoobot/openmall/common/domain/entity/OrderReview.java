package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 订单评价实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_review")
public class OrderReview extends BaseEntity {

    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "images", columnDefinition = "TEXT")
    private String images;

    @Column(name = "is_anonymous")
    private Integer isAnonymous = 0;

    @Column(name = "reply_content", length = 1000)
    private String replyContent;

    @Column(name = "reply_time")
    private LocalDateTime replyTime;
}
