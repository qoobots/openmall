package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.merchant.dto.OrderVO;
import com.qoobot.openmall.merchant.dto.PageResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 订单Service
 */
public interface OrderService {

    /**
     * 分页查询订单
     */
    PageResult<OrderVO> queryPage(Integer status, String keyword, Pageable pageable, Long shopId);

    /**
     * 根据ID查询订单
     */
    OrderVO getById(Long id, Long shopId);

    /**
     * 根据订单号查询订单
     */
    OrderVO getByOrderNo(String orderNo, Long shopId);

    /**
     * 订单发货
     */
    void ship(Long orderId, String logisticsCompany, String logisticsNo, Long shopId);

    /**
     * 批量发货
     */
    void batchShip(List<Long> orderIds, String logisticsCompany, String logisticsNo, Long shopId);
}
