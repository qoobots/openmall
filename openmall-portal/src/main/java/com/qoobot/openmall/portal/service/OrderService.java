package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.portal.dto.OrderSubmitDTO;
import com.qoobot.openmall.portal.vo.OrderConfirmVO;
import com.qoobot.openmall.portal.vo.OrderDetailVO;
import com.qoobot.openmall.portal.vo.OrderVO;

import java.math.BigDecimal;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 获取订单确认页VO
     */
    OrderConfirmVO getConfirmVO(Long userId, Long skuId, Integer quantity);

    /**
     * 提交订单
     */
    String submitOrder(OrderSubmitDTO dto);

    /**
     * 根据订单号查询订单
     */
    OrderDetailVO getOrderByNo(String orderNo);

    /**
     * 查询订单详情
     */
    OrderDetailVO getOrderDetail(Long id);

    /**
     * 查询用户订单列表
     */
    PageResult<OrderVO> getUserOrders(Long userId, Integer status, Integer pageNum);

    /**
     * 取消订单
     */
    void cancelOrder(Long id, String reason);

    /**
     * 确认收货
     */
    void confirmReceipt(Long id);

    /**
     * 删除订单
     */
    void deleteOrder(Long userId, String orderNo);
}
