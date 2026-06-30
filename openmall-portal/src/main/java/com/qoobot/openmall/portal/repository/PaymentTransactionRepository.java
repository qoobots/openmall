package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByOrderNo(String orderNo);
    Optional<PaymentTransaction> findByPayNo(String payNo);
    Optional<PaymentTransaction> findByOrderNoAndPayStatus(String orderNo, Integer payStatus);
}
