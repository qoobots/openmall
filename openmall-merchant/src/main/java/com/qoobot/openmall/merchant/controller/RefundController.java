package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.RefundOrder;
import com.qoobot.openmall.portal.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商家退款管理控制器
 */
@Controller
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 退款列表页
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        Long shopId = getShopId();
        PageResult<RefundOrder> page = refundService.listShopRefunds(shopId, pageNum, 10);
        model.addAttribute("page", page);
        return "refund/list";
    }

    /**
     * 退款详情
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("refund", refundService.getRefundDetail(id));
        return "refund/detail";
    }

    /**
     * 审核退款
     */
    @PostMapping("/audit")
    @ResponseBody
    public Result<RefundOrder> audit(@RequestBody Map<String, Object> params) {
        Long refundId = Long.valueOf(params.get("refundId").toString());
        Integer result = Integer.valueOf(params.get("result").toString());
        String reason = (String) params.getOrDefault("reason", "");
        return Result.success(refundService.auditRefund(refundId, result, reason, getShopId()));
    }

    private Long getShopId() {
        return 1L; // TODO: 从 Session 获取
    }
}
