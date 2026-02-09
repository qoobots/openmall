package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 订单主表Repository
 */
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long> {

    /**
     * 根据订单号查询订单
     */
    Optional<OrderMaster> findByOrderNo(String orderNo);

    /**
     * 根据用户ID分页查询订单
     */
    Page<OrderMaster> findByUserIdAndIsDeletedOrderByCreateTimeDesc(Long userId, Integer isDeleted, Pageable pageable);

    /**
     * 根据用户ID和订单状态查询
     */
    Page<OrderMaster> findByUserIdAndOrderStatusAndIsDeletedOrderByCreateTimeDesc(
            Long userId, Integer orderStatus, Integer isDeleted, Pageable pageable);
}
