package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.Shop;
import com.qoobot.openmall.merchant.dto.ShopDTO;
import com.qoobot.openmall.merchant.repository.ShopRepository;
import com.qoobot.openmall.merchant.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 店铺Service实现
 */
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public ShopDTO getByMerchantId(Long merchantId) {
        return shopRepository.findByMerchantId(merchantId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional
    @Override
    public void save(ShopDTO dto, Long merchantId) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(dto, shop);
        shop.setMerchantId(merchantId);
        shop.setStatus(1); // 默认启用
        shop.setCreateTime(LocalDateTime.now());
        shop.setUpdateTime(LocalDateTime.now());
        shop.setCreateBy(merchantId); // TODO: 修改为当前用户ID
        shop.setUpdateBy(merchantId); // TODO: 修改为当前用户ID
        shop.setIsDeleted(0);
        shop.setVersion(0);

        shopRepository.save(shop);
    }

    @Transactional
    @Override
    public void update(ShopDTO dto, Long merchantId) {
        Shop shop = shopRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new RuntimeException("店铺不存在"));

        // 验证权限
        if (!shop.getMerchantId().equals(merchantId)) {
            throw new RuntimeException("无权修改该店铺");
        }

        // 更新店铺信息
        BeanUtils.copyProperties(dto, shop, "id", "merchantId", "createTime", "createBy");
        shop.setUpdateTime(LocalDateTime.now());
        shop.setUpdateBy(merchantId); // TODO: 修改为当前用户ID

        shopRepository.save(shop);
    }

    @Override
    public boolean existsByMerchantId(Long merchantId) {
        return shopRepository.existsByMerchantId(merchantId);
    }

    /**
     * 转换为DTO
     */
    private ShopDTO convertToDTO(Shop shop) {
        ShopDTO dto = new ShopDTO();
        BeanUtils.copyProperties(shop, dto);
        return dto;
    }
}
