package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shop")
public class Shop extends BaseEntity {

    @Column(name = "merchant_id", nullable = false)
    private Long merchantId;

    @Column(name = "shop_name", nullable = false, length = 100)
    private String shopName;

    @Column(name = "shop_logo", length = 255)
    private String shopLogo;

    @Column(name = "shop_desc", length = 500)
    private String shopDesc;

    @Column(name = "shop_type", nullable = false)
    private Integer shopType;

    @Column(name = "service_phone", length = 20)
    private String servicePhone;
}
