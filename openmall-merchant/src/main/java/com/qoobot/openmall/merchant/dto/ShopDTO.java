package com.qoobot.openmall.merchant.dto;

import lombok.Data;

/**
 * 店铺DTO
 */
@Data
public class ShopDTO {
    private Long id;
    private Long merchantId;
    private String shopName;
    private String shopLogo;
    private String shopDesc;
    private Integer shopType;
    private String servicePhone;
    private String serviceEmail;
    private String serviceQq;
    private String serviceWechat;
    private Integer status;
}
