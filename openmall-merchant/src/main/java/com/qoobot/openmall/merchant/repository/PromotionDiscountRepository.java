package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.PromotionDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 限时折扣 Repository
 */
@Repository
public interface PromotionDiscountRepository extends JpaRepository<PromotionDiscount, Long> {

    Page<PromotionDiscount> findByShopIdAndIsDeletedOrderByCreateTimeDesc(Long shopId, Integer isDeleted, Pageable pageable);

    List<PromotionDiscount> findByShopIdAndStatusAndIsDeleted(Long shopId, Integer status, Integer isDeleted);
}
