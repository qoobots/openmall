package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户地址VO
 */
@Data
public class UserAddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String consigneeName;
    private String consigneePhone;
    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private String fullAddress;
    private Integer isDefault;
}
