package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.PromotionSeckill;

/**
 * 秒杀活动服务
 */
public interface PromotionSeckillService {

    PageResult<PromotionSeckill> queryPage(Long shopId, Integer pageNum, Integer pageSize);

    PromotionSeckill getById(Long id, Long shopId);

    Long save(PromotionSeckill entity, Long shopId);

    void update(PromotionSeckill entity, Long shopId);

    void delete(Long id, Long shopId);
}
