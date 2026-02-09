package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品SKU数据访问层
 *
 * @author openmall
 */
@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

    /**
     * 根据SPU ID查询SKU列表
     */
    List<ProductSku> findBySpuIdAndIsDeleted(Long spuId, Integer isDeleted);

    /**
     * 根据ID查询
     */
    ProductSku findByIdAndIsDeleted(Long id, Integer isDeleted);
}
