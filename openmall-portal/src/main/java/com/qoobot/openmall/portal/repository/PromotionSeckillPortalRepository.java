package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.PromotionSeckill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 前台秒杀 Repository
 */
@Repository
public interface PromotionSeckillPortalRepository extends JpaRepository<PromotionSeckill, Long> {

    @Query("SELECT p FROM PromotionSeckill p WHERE p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    List<PromotionSeckill> findActiveSeckills();

    @Query("SELECT p FROM PromotionSeckill p WHERE p.skuId = :skuId AND p.status = 1 AND p.isDeleted = 0 AND p.startTime <= CURRENT_TIMESTAMP AND p.endTime >= CURRENT_TIMESTAMP")
    PromotionSeckill findActiveBySkuId(@Param("skuId") Long skuId);
}
