package com.qoobot.openmall.portal.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.qoobot.openmall.common.domain.entity.OrderMaster;
import com.qoobot.openmall.common.domain.entity.PaymentTransaction;
import com.qoobot.openmall.portal.repository.OrderMasterRepository;
import com.qoobot.openmall.portal.repository.PaymentTransactionRepository;
import com.qoobot.openmall.portal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentRepo;
    private final OrderMasterRepository orderMasterRepo;

    @Override
    @Transactional
    public PaymentTransaction createPayment(String orderNo, Integer payType) {
        Optional<PaymentTransaction> existing = paymentRepo.findByOrderNoAndPayStatus(orderNo, 1);
        if (existing.isPresent()) {
            return existing.get();
        }

        // 获取订单
        OrderMaster order = orderMasterRepo.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        PaymentTransaction payment = new PaymentTransaction();
        payment.setOrderNo(orderNo);
        payment.setPayNo("PAY" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + IdUtil.fastSimpleUUID().substring(0, 8));
        payment.setPayType(payType);
        payment.setPayAmount(order.getPayAmount());
        payment.setPayStatus(0);
        payment.setCreateBy(1L);
        payment.setUpdateBy(1L);
        return paymentRepo.save(payment);
    }

    @Override
    @Transactional
    public PaymentTransaction pay(String orderNo, Integer payType) {
        PaymentTransaction payment = paymentRepo.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("支付单不存在，请先创建支付"));

        OrderMaster order = orderMasterRepo.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        // 模拟支付成功
        payment.setPayStatus(1);
        payment.setPayTime(LocalDateTime.now());
        payment.setOutTradeNo("OUT_" + IdUtil.fastSimpleUUID().substring(0, 16));
        payment.setBuyerName("测试买家");
        payment.setUpdateBy(1L);

        // 更新订单状态
        order.setOrderStatus(2); // 待发货
        order.setPayType(payType);
        order.setPayTime(LocalDateTime.now());
        orderMasterRepo.save(order);

        return paymentRepo.save(payment);
    }

    @Override
    public PaymentTransaction queryPayment(String orderNo) {
        return paymentRepo.findByOrderNo(orderNo).orElse(null);
    }

    @Override
    @Transactional
    public void handlePayNotify(String payNo, String outTradeNo, Integer payStatus, String notifyData) {
        paymentRepo.findByPayNo(payNo).ifPresent(payment -> {
            payment.setPayStatus(payStatus);
            payment.setOutTradeNo(outTradeNo);
            payment.setNotifyData(notifyData);
            if (payStatus == 1) {
                payment.setPayTime(LocalDateTime.now());
            }
            paymentRepo.save(payment);
        });
    }
}
