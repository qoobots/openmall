package com.qoobot.openmall.merchant.dto;

import lombok.Data;

/**
 * 商品查询条件
 */
@Data
public class ProductQuery {
    private Long categoryId;
    private Integer status;
    private String keyword;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
