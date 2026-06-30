package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.StockLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 库存日志 Repository
 */
@Repository
public interface StockLogRepository extends JpaRepository<StockLog, Long> {

    /**
     * 根据SKU ID分页查询库存日志
     */
    Page<StockLog> findBySkuIdAndIsDeletedOrderByCreateTimeDesc(Long skuId, Integer isDeleted, Pageable pageable);

    /**
     * 根据关联单号查询
     */
    List<StockLog> findByRelatedNoAndIsDeleted(String relatedNo, Integer isDeleted);
}
