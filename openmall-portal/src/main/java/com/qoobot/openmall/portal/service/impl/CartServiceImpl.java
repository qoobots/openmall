package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.domain.entity.CartItem;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.portal.repository.CartItemRepository;
import com.qoobot.openmall.portal.repository.ProductSkuRepository;
import com.qoobot.openmall.portal.service.CartService;
import com.qoobot.openmall.portal.vo.CartVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 购物车服务实现
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductSkuRepository productSkuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(Long userId, Long skuId, Integer quantity) {
        // 参数校验
        if (userId == null || skuId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("参数不正确");
        }

        // 检查SKU是否存在
        ProductSku sku = productSkuRepository.findById(skuId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        if (sku.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }

        // 检查购物车是否已存在该商品
        CartItem existingItem = cartItemRepository.findByUserIdAndSkuIdAndIsDeleted(userId, skuId, 0);

        if (existingItem != null) {
            // 更新数量
            int newQuantity = existingItem.getQuantity() + quantity;
            if (sku.getStock() < newQuantity) {
                throw new RuntimeException("库存不足，当前库存：" + sku.getStock());
            }
            existingItem.setQuantity(newQuantity);
            existingItem.setUpdateBy(userId);
            cartItemRepository.save(existingItem);
        } else {
            // 新增购物车记录
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setSkuId(skuId);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelected(1);
            cartItem.setCreateBy(userId);
            cartItem.setUpdateBy(userId);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(Long userId, Long skuId, Integer quantity) {
        // 参数校验
        if (userId == null || skuId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("参数不正确");
        }

        CartItem cartItem = cartItemRepository.findByUserIdAndSkuIdAndIsDeleted(userId, skuId, 0);
        if (cartItem == null) {
            throw new RuntimeException("购物车商品不存在");
        }

        // 检查库存
        ProductSku sku = productSkuRepository.findById(skuId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        if (sku.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }

        cartItem.setQuantity(quantity);
        cartItem.setUpdateBy(userId);
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCartItem(Long userId, Long skuId) {
        if (userId == null || skuId == null) {
            throw new IllegalArgumentException("参数不正确");
        }

        CartItem cartItem = cartItemRepository.findByUserIdAndSkuIdAndIsDeleted(userId, skuId, 0);
        if (cartItem != null) {
            cartItem.setIsDeleted(1);
            cartItem.setUpdateBy(userId);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartVO> listCartItems(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        List<CartItem> cartItems = cartItemRepository.findByUserIdAndIsDeletedOrderByCreateTimeDesc(userId, 0);

        return cartItems.stream().map(cartItem -> {
            ProductSku sku = productSkuRepository.findById(cartItem.getSkuId()).orElse(null);
            CartVO vo = new CartVO();
            vo.setId(cartItem.getId());
            vo.setSkuId(cartItem.getSkuId());
            vo.setQuantity(cartItem.getQuantity());
            vo.setIsSelected(cartItem.getIsSelected());

            if (sku != null) {
                vo.setPrice(sku.getPrice());
                vo.setStock(sku.getStock());
                // 这里可以从SPU表获取商品信息，暂时设置为默认值
                vo.setProductName("商品-" + sku.getId());
                vo.setMainImage("");
                vo.setSpecs(sku.getSpecs());
                vo.setAmount(sku.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            }

            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleSelect(Long userId, Long skuId) {
        if (userId == null || skuId == null) {
            throw new IllegalArgumentException("参数不正确");
        }

        CartItem cartItem = cartItemRepository.findByUserIdAndSkuIdAndIsDeleted(userId, skuId, 0);
        if (cartItem != null) {
            cartItem.setIsSelected(cartItem.getIsSelected() == 1 ? 0 : 1);
            cartItem.setUpdateBy(userId);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleSelectAll(Long userId, Integer isSelected) {
        if (userId == null || isSelected == null) {
            throw new IllegalArgumentException("参数不正确");
        }

        // 使用批量更新替代循环逐个更新
        cartItemRepository.updateSelectStatusByUserId(userId, isSelected);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateSelectedAmount(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        List<CartItem> cartItems = cartItemRepository.findByUserIdAndIsDeleted(userId, 0);
        return cartItems.stream()
                .filter(item -> item.getIsSelected() != null && item.getIsSelected() == 1)
                .map(cartItem -> {
                    ProductSku sku = productSkuRepository.findById(cartItem.getSkuId()).orElse(null);
                    if (sku != null && sku.getPrice() != null && cartItem.getQuantity() != null) {
                        return sku.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
                    }
                    return BigDecimal.ZERO;
                })
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long userId, List<Long> skuIds) {
        if (userId == null || skuIds == null || skuIds.isEmpty()) {
            throw new IllegalArgumentException("参数不正确");
        }
        cartItemRepository.batchDeleteBySkuIds(userId, skuIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 使用批量删除替代循环逐个更新
        cartItemRepository.batchDeleteByUserId(userId);
    }
}
