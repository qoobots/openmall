package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 基础实体类
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "create_by", nullable = false)
    private Long createBy;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @LastModifiedBy
    @Column(name = "update_by", nullable = false)
    private Long updateBy;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted = 0;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version = 0;
}
