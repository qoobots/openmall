package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价 VO
 */
@Data
public class ReviewVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long spuId;
    private Long skuId;
    private Integer rating;
    private String content;
    private String images;
    private Integer isAnonymous;
    private String replyContent;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
}
