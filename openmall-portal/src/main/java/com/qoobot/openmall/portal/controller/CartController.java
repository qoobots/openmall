package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.dto.CartItemDTO;
import com.qoobot.openmall.portal.vo.CartVO;
import com.qoobot.openmall.portal.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车控制器
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final com.qoobot.openmall.portal.service.CartService cartService;

    /**
     * 购物车页面
     */
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        Long userId = getUserId(session);
        List<CartVO> cartList = cartService.listCartItems(userId);
        model.addAttribute("cartList", cartList);

        // 计算选中商品总金额
        BigDecimal totalAmount = cartList.stream()
                .filter(cart -> cart.getIsSelected() != null && cart.getIsSelected() == 1)
                .map(cart -> cart.getPrice().multiply(new BigDecimal(cart.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalAmount", totalAmount);

        return "cart/index";
    }

    /**
     * 添加到购物车
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Void> add(@RequestBody CartItemDTO dto, HttpSession session) {
        Long userId = getUserId(session);
        cartService.addToCart(userId, dto.getSkuId(), dto.getQuantity());
        return Result.success("已加入购物车");
    }

    /**
     * 更新购物车数量
     */
    @PutMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody CartItemDTO dto, HttpSession session) {
        Long userId = getUserId(session);
        cartService.updateQuantity(userId, dto.getSkuId(), dto.getQuantity());
        return Result.success();
    }

    /**
     * 删除购物车商品
     */
    @DeleteMapping("/delete/{skuId}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long skuId, HttpSession session) {
        Long userId = getUserId(session);
        cartService.deleteCartItem(userId, skuId);
        return Result.success();
    }

    /**
     * 选择/取消选择商品
     */
    @PutMapping("/toggle-select/{skuId}")
    @ResponseBody
    public Result<Void> toggleSelect(@PathVariable Long skuId, HttpSession session) {
        Long userId = getUserId(session);
        cartService.toggleSelect(userId, skuId);
        return Result.success();
    }

    /**
     * 全选/取消全选
     */
    @PutMapping("/toggle-select-all")
    @ResponseBody
    public Result<Void> toggleSelectAll(@RequestParam Integer isSelected, HttpSession session) {
        Long userId = getUserId(session);
        cartService.toggleSelectAll(userId, isSelected);
        return Result.success();
    }

    /**
     * 计算选中商品总金额
     */
    @GetMapping("/total-amount")
    @ResponseBody
    public Result<BigDecimal> getTotalAmount(HttpSession session) {
        Long userId = getUserId(session);
        BigDecimal totalAmount = cartService.calculateSelectedAmount(userId);
        return Result.success(totalAmount);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    @ResponseBody
    public Result<Void> clear(HttpSession session) {
        Long userId = getUserId(session);
        cartService.clearCart(userId);
        return Result.success();
    }

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L; // 测试用户
        }
        return userId;
    }
}
