package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商家实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "merchant")
public class Merchant extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "business_license", length = 100)
    private String businessLicense;

    @Column(name = "contact_name", nullable = false, length = 50)
    private String contactName;

    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "status", nullable = false)
    private Integer status = 0;
}
