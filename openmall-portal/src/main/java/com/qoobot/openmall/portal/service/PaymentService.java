package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.domain.entity.PaymentTransaction;

public interface PaymentService {

    /**
     * 创建支付单
     */
    PaymentTransaction createPayment(String orderNo, Integer payType);

    /**
     * 模拟支付（实际应调用第三方支付接口）
     */
    PaymentTransaction pay(String orderNo, Integer payType);

    /**
     * 查询支付状态
     */
    PaymentTransaction queryPayment(String orderNo);

    /**
     * 支付回调处理
     */
    void handlePayNotify(String payNo, String outTradeNo, Integer payStatus, String notifyData);
}
