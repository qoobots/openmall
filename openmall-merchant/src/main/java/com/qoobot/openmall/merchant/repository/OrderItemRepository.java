package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单明细Repository
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * 根据订单号查询订单明细
     */
    List<OrderItem> findByOrderNo(String orderNo);
    
    /**
     * 根据订单号列表查询订单明细
     */
    @Query("SELECT o FROM OrderItem o WHERE o.orderNo IN :orderNos")
    List<OrderItem> findByOrderNos(@Param("orderNos") List<String> orderNos);
}
