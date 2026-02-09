package com.qoobot.openmall.portal.dto;

import lombok.Data;

/**
 * 商品查询DTO
 *
 * @author openmall
 */
@Data
public class ProductQuery {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 排序方式
     */
    private String sortBy;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;
}
