package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存出入库记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "stock_log")
public class StockLog extends BaseEntity {

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "change_type", nullable = false)
    private Integer changeType;

    @Column(name = "change_count", nullable = false)
    private Integer changeCount;

    @Column(name = "before_stock", nullable = false)
    private Integer beforeStock;

    @Column(name = "after_stock", nullable = false)
    private Integer afterStock;

    @Column(name = "related_no", length = 32)
    private String relatedNo;

    @Column(name = "remark", length = 500)
    private String remark;
}
