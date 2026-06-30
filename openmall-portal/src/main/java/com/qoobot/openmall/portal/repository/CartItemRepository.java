package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.List;

/**
 * 购物车Repository
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * 根据用户ID和SKU ID查询购物车记录
     */
    CartItem findByUserIdAndSkuIdAndIsDeleted(Long userId, Long skuId, Integer isDeleted);

    /**
     * 根据用户ID查询购物车列表（按创建时间倒序）
     */
    List<CartItem> findByUserIdAndIsDeletedOrderByCreateTimeDesc(Long userId, Integer isDeleted);

    /**
     * 根据用户ID查询购物车列表
     */
    List<CartItem> findByUserIdAndIsDeleted(Long userId, Integer isDeleted);

    /**
     * 批量更新选中状态
     */
    @Modifying
    @Query("UPDATE CartItem c SET c.isSelected = :isSelected, c.updateBy = :userId WHERE c.userId = :userId AND c.isDeleted = 0")
    int updateSelectStatusByUserId(@Param("userId") Long userId, @Param("isSelected") Integer isSelected);

    /**
     * 批量逻辑删除购物车项
     */
    @Modifying
    @Query("UPDATE CartItem c SET c.isDeleted = 1, c.updateBy = :userId WHERE c.userId = :userId AND c.isDeleted = 0")
    int batchDeleteByUserId(@Param("userId") Long userId);

    /**
     * 批量删除指定SKU的购物车项
     */
    @Modifying
    @Query("UPDATE CartItem c SET c.isDeleted = 1, c.updateBy = :userId WHERE c.userId = :userId AND c.skuId IN :skuIds AND c.isDeleted = 0")
    int batchDeleteBySkuIds(@Param("userId") Long userId, @Param("skuIds") List<Long> skuIds);
}
