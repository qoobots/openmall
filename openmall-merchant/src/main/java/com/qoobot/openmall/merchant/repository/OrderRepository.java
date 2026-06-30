package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 订单Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderMaster, Long>, JpaSpecificationExecutor<OrderMaster> {
    
    /**
     * 根据店铺ID分页查询订单
     */
    Page<OrderMaster> findByShopId(Long shopId, Pageable pageable);
    
    /**
     * 根据店铺ID和订单状态分页查询
     */
    Page<OrderMaster> findByShopIdAndOrderStatus(Long shopId, Integer orderStatus, Pageable pageable);
    
    /**
     * 根据订单号查询订单
     */
    OrderMaster findByOrderNo(String orderNo);
    
    /**
     * 统计店铺订单数量
     */
    long countByShopId(Long shopId);
    
    /**
     * 统计店铺不同状态订单数量
     */
    long countByShopIdAndOrderStatus(Long shopId, Integer orderStatus);
}
