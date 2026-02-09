package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * 根据订单号和SKU ID查询
     */
    OrderItem findByOrderNoAndSkuId(String orderNo, Long skuId);
}
