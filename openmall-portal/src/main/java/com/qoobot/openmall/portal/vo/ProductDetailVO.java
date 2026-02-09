package com.qoobot.openmall.portal.vo;

import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.common.domain.entity.ProductSpu;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情VO
 *
 * @author openmall
 */
@Data
public class ProductDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SPU
     */
    private ProductSpu product;

    /**
     * SKU列表
     */
    private List<ProductSku> skuList;

    /**
     * 销售属性
     */
    private List<SaleAttributeVO> saleAttributes;

    /**
     * 最低价格
     */
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    private BigDecimal maxPrice;

    /**
     * 总库存
     */
    private Integer stock;

    /**
     * 评价数量
     */
    private Integer reviewCount;
}
