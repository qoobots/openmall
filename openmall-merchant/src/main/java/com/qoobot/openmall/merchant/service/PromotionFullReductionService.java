package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.PromotionFullReduction;

/**
 * 满减活动服务
 */
public interface PromotionFullReductionService {

    PageResult<PromotionFullReduction> queryPage(Long shopId, Integer pageNum, Integer pageSize);

    PromotionFullReduction getById(Long id, Long shopId);

    Long save(PromotionFullReduction entity, Long shopId);

    void update(PromotionFullReduction entity, Long shopId);

    void delete(Long id, Long shopId);
}
