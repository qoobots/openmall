package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品SKU Repository
 */
@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
    
    /**
     * 根据SPU ID查询SKU列表
     */
    List<ProductSku> findBySpuId(Long spuId);
    
    /**
     * 根据SPU ID删除SKU
     */
    @Modifying
    @Query("DELETE FROM ProductSku p WHERE p.spuId = :spuId")
    void deleteBySpuId(@Param("spuId") Long spuId);
    
    /**
     * 根据SPU ID统计SKU数量
     */
    long countBySpuId(Long spuId);
    
    /**
     * 根据SPU ID和规格查询SKU
     */
    @Query("SELECT p FROM ProductSku p WHERE p.spuId = :spuId AND p.specs = :specs")
    ProductSku findBySpuIdAndSpecs(@Param("spuId") Long spuId, @Param("specs") String specs);
}
