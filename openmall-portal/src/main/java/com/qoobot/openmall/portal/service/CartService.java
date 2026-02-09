package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.vo.CartVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车服务接口
 */
public interface CartService {

    /**
     * 添加商品到购物车
     */
    void addToCart(Long userId, Long skuId, Integer quantity);

    /**
     * 更新购物车商品数量
     */
    void updateQuantity(Long userId, Long skuId, Integer quantity);

    /**
     * 删除购物车商品
     */
    void deleteCartItem(Long userId, Long skuId);

    /**
     * 查询购物车列表
     */
    List<CartVO> listCartItems(Long userId);

    /**
     * 选择/取消选择购物车商品
     */
    void toggleSelect(Long userId, Long skuId);

    /**
     * 全选/取消全选
     */
    void toggleSelectAll(Long userId, Integer isSelected);

    /**
     * 计算选中商品总金额
     */
    BigDecimal calculateSelectedAmount(Long userId);

    /**
     * 清空购物车
     */
    void clearCart(Long userId);
}
