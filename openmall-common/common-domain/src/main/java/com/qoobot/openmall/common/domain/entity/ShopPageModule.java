package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺页面模块实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shop_page_module")
public class ShopPageModule extends BaseEntity {

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "module_name", nullable = false, length = 50)
    private String moduleName;

    @Column(name = "module_type", nullable = false)
    private Integer moduleType;

    @Column(name = "module_config", columnDefinition = "TEXT")
    private String moduleConfig;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_enabled")
    private Integer isEnabled = 1;
}
