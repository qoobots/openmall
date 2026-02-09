package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_selected", nullable = false)
    private Integer isSelected = 1;
}
