package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.PromotionDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 前台限时折扣 Repository
 */
@Repository
public interface PromotionDiscountPortalRepository extends JpaRepository<PromotionDiscount, Long> {

    @Query("SELECT p FROM PromotionDiscount p WHERE p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    List<PromotionDiscount> findActiveDiscounts();

    @Query("SELECT p FROM PromotionDiscount p WHERE p.spuId = :spuId AND p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    PromotionDiscount findActiveBySpuId(@Param("spuId") Long spuId);
}
