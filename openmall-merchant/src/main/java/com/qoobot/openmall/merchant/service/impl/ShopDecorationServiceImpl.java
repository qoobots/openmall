package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.ShopPageModule;
import com.qoobot.openmall.merchant.repository.ShopPageModuleRepository;
import com.qoobot.openmall.merchant.service.ShopDecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopDecorationServiceImpl implements ShopDecorationService {

    private final ShopPageModuleRepository repository;

    @Override
    public List<ShopPageModule> getModules(Long shopId) {
        return repository.findByShopIdAndIsDeletedOrderBySortOrderAsc(shopId, 0);
    }

    @Override
    public ShopPageModule getById(Long id, Long shopId) {
        return repository.findById(id)
                .filter(p -> p.getShopId().equals(shopId) && p.getIsDeleted() == 0)
                .orElseThrow(() -> new RuntimeException("页面模块不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(ShopPageModule entity, Long shopId) {
        entity.setShopId(shopId);
        entity.setIsEnabled(1);
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
    public void update(ShopPageModule entity, Long shopId) {
        ShopPageModule existing = getById(entity.getId(), shopId);
        existing.setModuleName(entity.getModuleName());
        existing.setModuleType(entity.getModuleType());
        existing.setModuleConfig(entity.getModuleConfig());
        existing.setIsEnabled(entity.getIsEnabled());
        existing.setSortOrder(entity.getSortOrder());
        existing.setUpdateTime(LocalDateTime.now());
        existing.setUpdateBy(shopId);
        repository.save(existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long shopId) {
        ShopPageModule existing = getById(id, shopId);
        existing.setIsDeleted(1);
        existing.setUpdateTime(LocalDateTime.now());
        existing.setUpdateBy(shopId);
        repository.save(existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSortOrder(Long id, Integer sortOrder, Long shopId) {
        ShopPageModule existing = getById(id, shopId);
        existing.setSortOrder(sortOrder);
        existing.setUpdateTime(LocalDateTime.now());
        existing.setUpdateBy(shopId);
        repository.save(existing);
    }
}
