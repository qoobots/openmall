package com.qoobot.openmall.merchant.repository;

import com.qoobot.openmall.common.domain.entity.PromotionFullReduction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 满减活动 Repository
 */
@Repository
public interface PromotionFullReductionRepository extends JpaRepository<PromotionFullReduction, Long> {

    Page<PromotionFullReduction> findByShopIdAndIsDeletedOrderByCreateTimeDesc(Long shopId, Integer isDeleted, Pageable pageable);

    List<PromotionFullReduction> findByShopIdAndStatusAndIsDeleted(Long shopId, Integer status, Integer isDeleted);
}
