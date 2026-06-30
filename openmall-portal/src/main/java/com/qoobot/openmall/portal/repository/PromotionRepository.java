package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.PromotionFullReduction;
import com.qoobot.openmall.common.domain.entity.PromotionDiscount;
import com.qoobot.openmall.common.domain.entity.PromotionSeckill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 前台营销活动 Repository
 */
@Repository
public interface PromotionRepository extends JpaRepository<PromotionFullReduction, Long> {

    @Query("SELECT p FROM PromotionFullReduction p WHERE p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    List<PromotionFullReduction> findActiveFullReductions();

    @Query("SELECT p FROM PromotionFullReduction p WHERE p.shopId = :shopId AND p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    List<PromotionFullReduction> findActiveFullReductionsByShopId(@Param("shopId") Long shopId);
}
