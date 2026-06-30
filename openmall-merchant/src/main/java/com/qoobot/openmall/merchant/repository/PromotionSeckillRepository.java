package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.PromotionSeckill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 秒杀活动 Repository
 */
@Repository
public interface PromotionSeckillRepository extends JpaRepository<PromotionSeckill, Long> {

    Page<PromotionSeckill> findByShopIdAndIsDeletedOrderByCreateTimeDesc(Long shopId, Integer isDeleted, Pageable pageable);

    List<PromotionSeckill> findByShopIdAndStatusAndIsDeleted(Long shopId, Integer status, Integer isDeleted);
}
