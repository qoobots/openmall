package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.OrderReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单评价 Repository
 */
@Repository
public interface OrderReviewRepository extends JpaRepository<OrderReview, Long> {

    Page<OrderReview> findBySpuIdAndIsDeletedOrderByCreateTimeDesc(Long spuId, Integer isDeleted, Pageable pageable);

    Page<OrderReview> findByUserIdAndIsDeletedOrderByCreateTimeDesc(Long userId, Integer isDeleted, Pageable pageable);

    boolean existsByOrderNoAndSpuIdAndIsDeleted(String orderNo, Long spuId, Integer isDeleted);

    List<OrderReview> findByOrderNoAndIsDeleted(String orderNo, Integer isDeleted);
}
