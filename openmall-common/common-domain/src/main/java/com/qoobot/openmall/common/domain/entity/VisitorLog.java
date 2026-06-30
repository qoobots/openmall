package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 访客记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "visitor_log")
public class VisitorLog extends BaseEntity {

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "spu_id")
    private Long spuId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "visit_time", nullable = false)
    private LocalDateTime visitTime;
}
