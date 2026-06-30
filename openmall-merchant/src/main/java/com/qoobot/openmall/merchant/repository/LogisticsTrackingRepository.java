package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.LogisticsTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogisticsTrackingRepository extends JpaRepository<LogisticsTracking, Long> {
    Optional<LogisticsTracking> findByOrderNo(String orderNo);
    Optional<LogisticsTracking> findByLogisticsNo(String logisticsNo);
}
