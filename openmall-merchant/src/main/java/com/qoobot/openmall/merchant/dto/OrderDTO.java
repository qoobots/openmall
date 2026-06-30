package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单DTO
 */
@Data
public class OrderDTO {
    private String orderNo;
    private Long userId;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeAddress;
    private BigDecimal freightAmount;
    private String remark;
    private List<OrderItemDTO> items;
}
