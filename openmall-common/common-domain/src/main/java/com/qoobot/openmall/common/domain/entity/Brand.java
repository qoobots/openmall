package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {

    @Column(name = "brand_name", nullable = false, length = 100)
    private String brandName;

    @Column(name = "brand_logo", length = 255)
    private String brandLogo;

    @Column(name = "brand_desc", length = 500)
    private String brandDesc;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_show", nullable = false)
    private Integer isShow = 1;
}
