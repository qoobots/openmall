package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单VO
 */
@Data
public class OrderVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private String username;
    private Long shopId;
    private Integer orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer payType;
    private LocalDateTime payTime;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeAddress;
    private String remark;
    private LocalDateTime createTime;
    private List<OrderItemVO> items;
    private String statusText;
    private String statusBadge;
    private Integer totalQuantity;
    private String shopName;
}
