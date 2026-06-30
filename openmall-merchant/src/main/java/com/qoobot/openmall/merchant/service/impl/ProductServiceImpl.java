package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.ProductSpu;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.merchant.dto.*;
import com.qoobot.openmall.merchant.repository.ProductSpuRepository;
import com.qoobot.openmall.merchant.repository.ProductSkuRepository;
import com.qoobot.openmall.merchant.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品Service实现
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductSpuRepository productSpuRepository;
    private final ProductSkuRepository productSkuRepository;

    @Override
    public PageResult<ProductVO> queryPage(ProductQuery query, Long shopId) {
        // 构建查询条件
        Specification<ProductSpu> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 店铺ID
            predicates.add(criteriaBuilder.equal(root.get("shopId"), shopId));

            // 分类筛选
            if (query.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), query.getCategoryId()));
            }

            // 状态筛选
            if (query.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isShelves"), query.getStatus()));
            }

            // 关键字搜索
            if (StringUtils.hasText(query.getKeyword())) {
                String keyword = "%" + query.getKeyword() + "%";
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("productName"), keyword),
                    criteriaBuilder.like(root.get("productCode"), keyword)
                ));
            }

            // 未删除
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 分页查询
        Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<ProductSpu> page = productSpuRepository.findAll(spec, pageable);

        // 转换为VO
        List<ProductVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(),
                query.getPageNum(), query.getPageSize());
    }

    @Override
    public ProductDTO getById(Long id, Long shopId) {
        ProductSpu productSpu = productSpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 验证店铺权限
        if (!productSpu.getShopId().equals(shopId)) {
            throw new RuntimeException("无权访问该商品");
        }

        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(productSpu, dto);

        // 查询SKU列表
        List<ProductSku> skuList = productSkuRepository.findBySpuId(id);
        List<ProductSkuDTO> skuDTOList = skuList.stream().map(sku -> {
            ProductSkuDTO skuDTO = new ProductSkuDTO();
            BeanUtils.copyProperties(sku, skuDTO);
            return skuDTO;
        }).collect(Collectors.toList());

        dto.setSkuList(skuDTOList);
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(ProductDTO dto, Long shopId) {
        // 保存SPU
        ProductSpu spu = new ProductSpu();
        BeanUtils.copyProperties(dto, spu);
        spu.setShopId(shopId);
        spu.setIsShelves(0); // 默认下架
        spu.setSales(0);
        spu.setViews(0);
        spu.setIsDeleted(0);
        spu.setVersion(0);
        spu.setCreateTime(LocalDateTime.now());
        spu.setUpdateTime(LocalDateTime.now());
        spu.setCreateBy(shopId); // TODO: 修改为当前用户ID
        spu.setUpdateBy(shopId); // TODO: 修改为当前用户ID

        // 处理相册图片
        if (dto.getAlbumImages() != null && !dto.getAlbumImages().isEmpty()) {
            spu.setAlbumImages(String.join(",", dto.getAlbumImages()));
        }

        ProductSpu savedSpu = productSpuRepository.save(spu);

        // 保存SKU
        if (dto.getSkuList() != null && !dto.getSkuList().isEmpty()) {
            for (ProductSkuDTO skuDTO : dto.getSkuList()) {
                ProductSku sku = new ProductSku();
                BeanUtils.copyProperties(skuDTO, sku);
                sku.setSpuId(savedSpu.getId());
                sku.setSales(0);
                sku.setIsDeleted(0);
                sku.setVersion(0);
                sku.setCreateTime(LocalDateTime.now());
                sku.setUpdateTime(LocalDateTime.now());
                sku.setCreateBy(shopId); // TODO: 修改为当前用户ID
                sku.setUpdateBy(shopId); // TODO: 修改为当前用户ID
                productSkuRepository.save(sku);
            }
        }

        return savedSpu.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ProductDTO dto, Long shopId) {
        ProductSpu spu = productSpuRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 验证店铺权限
        if (!spu.getShopId().equals(shopId)) {
            throw new RuntimeException("无权修改该商品");
        }

        // 更新SPU信息
        BeanUtils.copyProperties(dto, spu, "id", "shopId", "createTime", "createBy");
        spu.setUpdateTime(LocalDateTime.now());
        spu.setUpdateBy(shopId); // TODO: 修改为当前用户ID

        // 处理相册图片
        if (dto.getAlbumImages() != null && !dto.getAlbumImages().isEmpty()) {
            spu.setAlbumImages(String.join(",", dto.getAlbumImages()));
        }

        productSpuRepository.save(spu);

        // 删除旧SKU
        productSkuRepository.deleteBySpuId(spu.getId());

        // 保存新SKU
        if (dto.getSkuList() != null && !dto.getSkuList().isEmpty()) {
            for (ProductSkuDTO skuDTO : dto.getSkuList()) {
                ProductSku sku = new ProductSku();
                BeanUtils.copyProperties(skuDTO, sku);
                sku.setSpuId(spu.getId());
                sku.setSales(sku.getSales() != null ? sku.getSales() : 0);
                sku.setIsDeleted(0);
                sku.setVersion(0);
                sku.setCreateTime(LocalDateTime.now());
                sku.setUpdateTime(LocalDateTime.now());
                sku.setCreateBy(shopId); // TODO: 修改为当前用户ID
                sku.setUpdateBy(shopId); // TODO: 修改为当前用户ID
                productSkuRepository.save(sku);
            }
        }
    }

    @Override
    public void delete(Long id, Long shopId) {
        ProductSpu spu = productSpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 验证店铺权限
        if (!spu.getShopId().equals(shopId)) {
            throw new RuntimeException("无权删除该商品");
        }

        // 逻辑删除
        spu.setIsDeleted(1);
        spu.setUpdateTime(LocalDateTime.now());
        spu.setUpdateBy(shopId); // TODO: 修改为当前用户ID
        productSpuRepository.save(spu);
    }

    @Override
    public void changeShelvesStatus(Long id, Integer status, Long shopId) {
        ProductSpu spu = productSpuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));

        // 验证店铺权限
        if (!spu.getShopId().equals(shopId)) {
            throw new RuntimeException("无权操作该商品");
        }

        spu.setIsShelves(status);
        spu.setUpdateTime(LocalDateTime.now());
        spu.setUpdateBy(shopId); // TODO: 修改为当前用户ID
        productSpuRepository.save(spu);
    }

    @Override
    public void batchShelves(String productIds, Long shopId) {
        List<Long> ids = Arrays.stream(productIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        for (Long id : ids) {
            changeShelvesStatus(id, 1, shopId);
        }
    }

    @Override
    public void batchUnshelves(String productIds, Long shopId) {
        List<Long> ids = Arrays.stream(productIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        for (Long id : ids) {
            changeShelvesStatus(id, 0, shopId);
        }
    }

    @Override
    public void batchDelete(String productIds, Long shopId) {
        List<Long> ids = Arrays.stream(productIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        for (Long id : ids) {
            delete(id, shopId);
        }
    }

    /**
     * 转换为VO
     */
    private ProductVO convertToVO(ProductSpu spu) {
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(spu, vo);

        // 查询SKU统计信息
        List<ProductSku> skuList = productSkuRepository.findBySpuId(spu.getId());
        
        if (skuList != null && !skuList.isEmpty()) {
            // 计算库存总量
            int totalStock = skuList.stream()
                    .mapToInt(ProductSku::getStock)
                    .sum();
            vo.setTotalStock(totalStock);

            // 计算价格区间
            vo.setMinPrice(skuList.stream()
                    .map(ProductSku::getPrice)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO));
            vo.setMaxPrice(skuList.stream()
                    .map(ProductSku::getPrice)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO));

            // SKU数量
            vo.setSkuCount(skuList.size());
        }

        // 处理相册图片
        if (spu.getAlbumImages() != null) {
            vo.setAlbumImages(Arrays.asList(spu.getAlbumImages().split(",")));
        }

        return vo;
    }
}
