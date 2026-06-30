package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.PromotionDiscount;
import com.qoobot.openmall.merchant.repository.PromotionDiscountRepository;
import com.qoobot.openmall.merchant.service.PromotionDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromotionDiscountServiceImpl implements PromotionDiscountService {

    private final PromotionDiscountRepository repository;

    @Override
    public PageResult<PromotionDiscount> queryPage(Long shopId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<PromotionDiscount> page = repository.findByShopIdAndIsDeletedOrderByCreateTimeDesc(shopId, 0, pageable);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    public PromotionDiscount getById(Long id, Long shopId) {
        return repository.findById(id)
                .filter(p -> p.getShopId().equals(shopId) && p.getIsDeleted() == 0)
                .orElseThrow(() -> new RuntimeException("限时折扣不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(PromotionDiscount entity, Long shopId) {
        entity.setShopId(shopId);
        entity.setSoldCount(0);
        entity.setStatus(0);
        entity.setIsDeleted(0);
        entity.setVersion(0);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateBy(shopId);
        entity.setUpdateBy(shopId);
        return repository.save(entity).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PromotionDiscount entity, Long shopId) {
        PromotionDiscount existing = getById(entity.getId(), shopId);
        existing.setPromotionName(entity.getPromotionName());
        existing.setSpuId(entity.getSpuId());
        existing.setSkuId(entity.getSkuId());
        existing.setDiscountPrice(entity.getDiscountPrice());
        existing.setDiscountLimit(entity.getDiscountLimit());
        existing.setTotalStock(entity.getTotalStock());
        existing.setStartTime(entity.getStartTime());
        existing.setEndTime(entity.getEndTime());
        existing.setUpdateTime(LocalDateTime.now());
        existing.setUpdateBy(shopId);
        repository.save(existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long shopId) {
        PromotionDiscount existing = getById(id, shopId);
        existing.setIsDeleted(1);
        existing.setUpdateTime(LocalDateTime.now());
        existing.setUpdateBy(shopId);
        repository.save(existing);
    }
}
