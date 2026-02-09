package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情VO
 */
@Data
public class OrderDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderNo;
    private Integer orderStatus;
    private String orderStatusDesc;
    private BigDecimal totalAmount;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer payType;
    private String payTypeDesc;
    private LocalDateTime payTime;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeAddress;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
