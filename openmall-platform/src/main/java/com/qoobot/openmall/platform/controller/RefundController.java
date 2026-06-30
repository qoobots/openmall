package com.qoobot.openmall.platform.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.RefundOrder;
import com.qoobot.openmall.platform.repository.RefundOrderRepository;
import com.qoobot.openmall.portal.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 平台退款仲裁控制器
 */
@Controller
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;
    private final RefundOrderRepository refundOrderRepository;

    /**
     * 退款列表页
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(required = false) Integer status,
                       Model model) {
        Pageable pageable = PageRequest.of(pageNum - 1, 10, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RefundOrder> page;
        if (status != null) {
            page = refundOrderRepository.findByRefundStatusAndIsDeleted(status, 0, pageable);
        } else {
            page = refundOrderRepository.findByIsDeletedOrderByCreateTimeDesc(0, pageable);
        }
        model.addAttribute("page", new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, 10));
        model.addAttribute("status", status);
        return "refund/list";
    }

    /**
     * 仲裁处理页
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("refund", refundService.getRefundDetail(id));
        return "refund/detail";
    }

    /**
     * 执行仲裁
     */
    @PostMapping("/arbitrate")
    @ResponseBody
    public Result<RefundOrder> arbitrate(@RequestBody Map<String, Object> params) {
        Long refundId = Long.valueOf(params.get("refundId").toString());
        Integer result = Integer.valueOf(params.get("result").toString());
        String remark = (String) params.getOrDefault("remark", "");
        return Result.success(refundService.arbitrateRefund(refundId, result, remark));
    }
}
