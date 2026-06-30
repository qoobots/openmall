package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.RefundOrder;
import com.qoobot.openmall.portal.service.RefundService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 退款控制器
 */
@Controller
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    /**
     * 申请退款页面
     */
    @GetMapping("/apply")
    public String applyPage(@RequestParam String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        return "refund/apply";
    }

    /**
     * 提交退款申请
     */
    @PostMapping("/apply")
    @ResponseBody
    public Result<RefundOrder> apply(@RequestBody Map<String, Object> params, HttpSession session) {
        Long userId = getUserId(session);
        String orderNo = (String) params.get("orderNo");
        String reason = (String) params.get("reason");
        Integer refundType = params.get("refundType") != null ? (Integer) params.get("refundType") : 1;
        return Result.success(refundService.applyRefund(userId, orderNo, reason, refundType));
    }

    /**
     * 我的退款列表
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       HttpSession session, Model model) {
        Long userId = getUserId(session);
        PageResult<RefundOrder> page = refundService.listUserRefunds(userId, pageNum, 10);
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

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L;
        }
        return userId;
    }
}
