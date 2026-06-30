package com.qoobot.openmall.platform.repository;

import com.qoobot.openmall.common.domain.entity.RefundOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 退款单 Repository (Platform)
 */
@Repository
public interface RefundOrderRepository extends JpaRepository<RefundOrder, Long> {

    Page<RefundOrder> findByIsDeletedOrderByCreateTimeDesc(Integer isDeleted, Pageable pageable);

    Page<RefundOrder> findByRefundStatusAndIsDeleted(Integer refundStatus, Integer isDeleted, Pageable pageable);
}
