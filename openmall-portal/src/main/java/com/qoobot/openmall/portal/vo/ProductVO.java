package com.qoobot.openmall.portal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品VO
 *
 * @author openmall
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品副标题
     */
    private String productSubTitle;

    /**
     * 商品主图
     */
    private String mainImage;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
