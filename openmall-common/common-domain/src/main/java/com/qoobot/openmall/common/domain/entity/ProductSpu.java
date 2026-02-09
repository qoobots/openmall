package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品SPU实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_spu")
public class ProductSpu extends BaseEntity {

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "product_sub_title", length = 200)
    private String productSubTitle;

    @Column(name = "product_desc", columnDefinition = "TEXT")
    private String productDesc;

    @Column(name = "main_image", length = 255)
    private String mainImage;

    @Column(name = "album_images", columnDefinition = "TEXT")
    private String albumImages;

    @Column(name = "is_shelves", nullable = false)
    private Integer isShelves = 0;

    @Column(name = "sales", nullable = false)
    private Integer sales = 0;

    @Column(name = "views")
    private Integer views = 0;
}
