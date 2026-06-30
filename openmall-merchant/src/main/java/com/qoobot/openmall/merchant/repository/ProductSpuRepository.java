package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.ProductSpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 商品SPU Repository
 */
@Repository
public interface ProductSpuRepository extends JpaRepository<ProductSpu, Long>, JpaSpecificationExecutor<ProductSpu> {
    
    /**
     * 根据店铺ID分页查询
     */
    Page<ProductSpu> findByShopId(Long shopId, Pageable pageable);
    
    /**
     * 根据店铺ID和状态查询
     */
    Page<ProductSpu> findByShopIdAndIsShelves(Long shopId, Integer isShelves, Pageable pageable);
    
    /**
     * 统计店铺商品数量
     */
    long countByShopId(Long shopId);
    
    /**
     * 统计店铺上架商品数量
     */
    long countByShopIdAndIsShelves(Long shopId, Integer isShelves);
}
