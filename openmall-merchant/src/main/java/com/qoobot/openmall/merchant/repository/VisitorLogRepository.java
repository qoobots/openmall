package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 访客记录 Repository
 */
@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long> {

    Long countByShopIdAndVisitTimeBetween(Long shopId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT v.userId) FROM VisitorLog v WHERE v.shopId = :shopId AND v.visitTime BETWEEN :start AND :end AND v.userId > 0")
    Long countDistinctVisitors(@Param("shopId") Long shopId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    Long countBySpuIdAndVisitTimeBetween(Long spuId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT v.spuId, COUNT(v) as cnt FROM VisitorLog v WHERE v.shopId = :shopId AND v.visitTime BETWEEN :start AND :end GROUP BY v.spuId ORDER BY cnt DESC")
    List<Object[]> findTopProducts(@Param("shopId") Long shopId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
