package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.PromotionDiscount;

/**
 * 限时折扣服务
 */
public interface PromotionDiscountService {

    PageResult<PromotionDiscount> queryPage(Long shopId, Integer pageNum, Integer pageSize);

    PromotionDiscount getById(Long id, Long shopId);

    Long save(PromotionDiscount entity, Long shopId);

    void update(PromotionDiscount entity, Long shopId);

    void delete(Long id, Long shopId);
}
