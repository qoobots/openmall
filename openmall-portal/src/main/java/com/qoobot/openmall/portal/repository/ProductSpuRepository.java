package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.ProductSpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 商品SPU数据访问层
 */
@Repository
public interface ProductSpuRepository extends JpaRepository<ProductSpu, Long>, JpaSpecificationExecutor<ProductSpu> {

    /**
     * 根据ID和删除状态查询
     */
    @Query("SELECT p FROM ProductSpu p WHERE p.id = ?1 AND p.isDeleted = 0")
    ProductSpu findByIdAndIsDeleted(Long id, Integer isDeleted);
}