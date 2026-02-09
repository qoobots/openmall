package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.dto.OrderSubmitDTO;
import com.qoobot.openmall.portal.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 确认订单页
     */
    @GetMapping("/order/confirm")
    public String confirm(@RequestParam(required = false) Long skuId,
                         @RequestParam(required = false) Integer quantity,
                         HttpSession session,
                         Model model) {
        Long userId = getUserId(session);
        
        // 如果没有指定skuId，从购物车获取
        if (skuId == null) {
            skuId = 1L;
            quantity = 1;
        }
        
        var confirmVO = orderService.getConfirmVO(userId, skuId, quantity);
        model.addAttribute("confirmVO", confirmVO);
        return "order/confirm";
    }

    /**
     * 提交订单
     */
    @PostMapping("/order/submit")
    @ResponseBody
    public Result<String> submit(@RequestBody OrderSubmitDTO dto, HttpSession session) {
        Long userId = getUserId(session);
        dto.setUserId(userId);
        String orderNo = orderService.submitOrder(dto);
        return Result.success("订单创建成功", orderNo);
    }

    /**
     * 支付页
     */
    @GetMapping("/order/payment")
    public String payment(@RequestParam String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("order", orderService.getOrderByNo(orderNo));
        return "order/payment";
    }

    /**
     * 订单详情
     */
    @GetMapping("/order/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderDetail(id));
        return "order/detail";
    }

    /**
     * 我的订单
     */
    @GetMapping("/order/list")
    public String list(@RequestParam(required = false) Integer status,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      HttpSession session,
                      Model model) {
        Long userId = getUserId(session);
        var pageResult = orderService.getUserOrders(userId, status, pageNum);
        model.addAttribute("page", pageResult);
        model.addAttribute("status", status);
        return "order/list";
    }

    /**
     * 取消订单
     */
    @PutMapping("/order/cancel/{id}")
    @ResponseBody
    public Result<Void> cancel(@PathVariable Long id, @RequestParam(required = false) String reason) {
        orderService.cancelOrder(id, reason);
        return Result.success();
    }

    /**
     * 确认收货
     */
    @PutMapping("/order/confirm/{id}")
    @ResponseBody
    public Result<Void> confirm(@PathVariable Long id) {
        orderService.confirmReceipt(id);
        return Result.success();
    }

    /**
     * 订单状态查询
     */
    @GetMapping("/order/status/{orderNo}")
    @ResponseBody
    public Result<Integer> getStatus(@PathVariable String orderNo) {
        var order = orderService.getOrderByNo(orderNo);
        return Result.success(order != null ? order.getOrderStatus() : null);
    }

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L;
        }
        return userId;
    }
}
