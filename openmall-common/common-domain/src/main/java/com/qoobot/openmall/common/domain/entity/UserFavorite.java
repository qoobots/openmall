package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户收藏实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_favorite", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "spu_id"}))
public class UserFavorite extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "spu_id", nullable = false)
    private Long spuId;
}
