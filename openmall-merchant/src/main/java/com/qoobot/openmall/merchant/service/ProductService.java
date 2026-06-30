package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.merchant.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 商品Service
 */
public interface ProductService {

    /**
     * 分页查询商品
     */
    PageResult<ProductVO> queryPage(ProductQuery query, Long shopId);

    /**
     * 根据ID查询商品SPU
     */
    ProductDTO getById(Long id, Long shopId);

    /**
     * 保存商品
     */
    Long save(ProductDTO dto, Long shopId);

    /**
     * 更新商品
     */
    void update(ProductDTO dto, Long shopId);

    /**
     * 删除商品
     */
    void delete(Long id, Long shopId);

    /**
     * 修改上架状态
     */
    void changeShelvesStatus(Long id, Integer status, Long shopId);

    /**
     * 批量上架
     */
    void batchShelves(String productIds, Long shopId);

    /**
     * 批量下架
     */
    void batchUnshelves(String productIds, Long shopId);

    /**
     * 批量删除
     */
    void batchDelete(String productIds, Long shopId);
}
