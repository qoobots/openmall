package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户收货地址实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_address")
public class UserAddress extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "consignee_name", nullable = false, length = 50)
    private String consigneeName;

    @Column(name = "consignee_phone", nullable = false, length = 20)
    private String consigneePhone;

    @Column(name = "province", nullable = false, length = 50)
    private String province;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "detail_address", nullable = false, length = 200)
    private String detailAddress;

    @Column(name = "is_default", nullable = false)
    private Integer isDefault = 0;
}
