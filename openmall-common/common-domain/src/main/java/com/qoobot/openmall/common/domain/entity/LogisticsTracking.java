package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "logistics_tracking")
public class LogisticsTracking extends BaseEntity {

    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    @Column(name = "logistics_no", nullable = false, length = 100)
    private String logisticsNo;

    @Column(name = "logistics_company", nullable = false, length = 100)
    private String logisticsCompany;

    @Column(name = "current_status", length = 100)
    private String currentStatus;

    @Column(name = "track_detail", columnDefinition = "TEXT")
    private String trackDetail;

    @Column(name = "estimated_arrive")
    private LocalDateTime estimatedArrive;

    @Column(name = "actual_arrive")
    private LocalDateTime actualArrive;

    @Column(name = "last_query_time")
    private LocalDateTime lastQueryTime;
}
