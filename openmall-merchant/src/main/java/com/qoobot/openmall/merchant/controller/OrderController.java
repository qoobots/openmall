package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.merchant.dto.*;
import com.qoobot.openmall.merchant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理控制器
 */
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 订单列表页
     */
    @GetMapping("/list")
    public String list(@RequestParam(required = false) Integer status,
                      @RequestParam(required = false) String keyword,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "20") Integer pageSize,
                      Model model) {
        // 分页查询
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        PageResult<OrderVO> pageResult = orderService.queryPage(status, keyword, pageable, getShopId());

        // 设置模型数据
        model.addAttribute("page", pageResult);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);

        return "order/list";
    }

    /**
     * 订单详情页
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        OrderVO order = orderService.getById(id, getShopId());
        model.addAttribute("order", order);
        return "order/detail";
    }

    /**
     * 订单发货
     */
    @PostMapping("/ship")
    @ResponseBody
    public Result<Void> ship(@RequestParam Long orderId,
                            @RequestParam String logisticsCompany,
                            @RequestParam String logisticsNo) {
        try {
            orderService.ship(orderId, logisticsCompany, logisticsNo, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量发货
     */
    @PostMapping("/batchShip")
    @ResponseBody
    public Result<Void> batchShip(@RequestParam String orderIds,
                                 @RequestParam String logisticsCompany,
                                 @RequestParam String logisticsNo) {
        try {
            List<Long> idList = Arrays.stream(orderIds.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            orderService.batchShip(idList, logisticsCompany, logisticsNo, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取当前店铺ID
     * TODO: 从Session或SecurityContext中获取
     */
    private Long getShopId() {
        // 临时返回1L，实际应从Session或SecurityContext中获取
        return 1L;
    }
}
