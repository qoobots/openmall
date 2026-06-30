package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.RefundOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 退款单 Repository
 */
@Repository
public interface RefundOrderRepository extends JpaRepository<RefundOrder, Long> {

    /**
     * 根据退款单号查询
     */
    Optional<RefundOrder> findByRefundNoAndIsDeleted(String refundNo, Integer isDeleted);

    /**
     * 根据原订单号查询
     */
    Optional<RefundOrder> findByOrderNoAndIsDeleted(String orderNo, Integer isDeleted);

    /**
     * 根据用户ID分页查询
     */
    Page<RefundOrder> findByUserIdAndIsDeletedOrderByCreateTimeDesc(Long userId, Integer isDeleted, Pageable pageable);

    /**
     * 根据店铺ID分页查询
     */
    Page<RefundOrder> findByShopIdAndIsDeletedOrderByCreateTimeDesc(Long shopId, Integer isDeleted, Pageable pageable);
}
