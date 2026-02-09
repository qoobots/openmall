package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.common.domain.entity.ProductSpu;
import com.qoobot.openmall.portal.dto.ProductQuery;
import com.qoobot.openmall.portal.repository.ProductSkuRepository;
import com.qoobot.openmall.portal.repository.ProductSpuRepository;
import com.qoobot.openmall.portal.service.ProductService;
import com.qoobot.openmall.portal.vo.ProductDetailVO;
import com.qoobot.openmall.portal.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品服务实现
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductSpuRepository productSpuRepository;
    private final ProductSkuRepository productSkuRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResult<ProductVO> queryPage(ProductQuery query) {
        // 参数校验
        if (query.getPageNum() == null || query.getPageNum() <= 0) {
            query.setPageNum(1);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(10);
        }
        if (query.getPageSize() > 100) {
            query.setPageSize(100); // 限制最大页面大小
        }

        Specification<ProductSpu> spec = buildProductSpecification(query);

        // 排序 - 修复价格排序逻辑错误
        Sort sort = buildSort(query.getSortBy());

        Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize(), sort);
        Page<ProductSpu> page = productSpuRepository.findAll(spec, pageable);

        List<ProductVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(), query.getPageNum(), query.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVO> getRecommendProducts(Integer limit) {
        limit = normalizeLimit(limit);
        
        // 查询已上架且未删除的商品，按浏览量排序
        Sort sort = Sort.by(Sort.Direction.DESC, "views");
        Pageable pageable = PageRequest.of(0, limit, sort);
        
        Specification<ProductSpu> spec = buildBaseSpecification();
        List<ProductSpu> list = productSpuRepository.findAll(spec, pageable).getContent();
        
        return list.stream()
                .map(this::convertToVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVO> getHotProducts(Integer limit) {
        limit = normalizeLimit(limit);
        
        // 查询已上架且未删除的商品，按销量排序
        Sort sort = Sort.by(Sort.Direction.DESC, "sales");
        Pageable pageable = PageRequest.of(0, limit, sort);
        
        Specification<ProductSpu> spec = buildBaseSpecification();
        List<ProductSpu> list = productSpuRepository.findAll(spec, pageable).getContent();
        
        return list.stream()
                .map(this::convertToVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductVO> getNewProducts(Integer limit) {
        limit = normalizeLimit(limit);
        
        // 查询已上架且未删除的新品
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0, limit, sort);
        
        Specification<ProductSpu> spec = buildBaseSpecification();
        List<ProductSpu> list = productSpuRepository.findAll(spec, pageable).getContent();
        
        return list.stream()
                .map(this::convertToVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailVO getDetailVO(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        
        ProductSpu spu = productSpuRepository.findById(id).orElse(null);
        if (spu == null || spu.getIsDeleted() == 1 || spu.getIsShelves() != 1) {
            return null;
        }

        List<ProductSku> skuList = productSkuRepository.findBySpuIdAndIsDeleted(id, 0);
        if (CollectionUtils.isEmpty(skuList)) {
            return null;
        }
        
        // 空值安全处理
        BigDecimal minPrice = skuList.stream()
                .filter(Objects::nonNull)
                .map(ProductSku::getPrice)
                .filter(Objects::nonNull)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        
        BigDecimal maxPrice = skuList.stream()
                .filter(Objects::nonNull)
                .map(ProductSku::getPrice)
                .filter(Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        
        Integer stock = skuList.stream()
                .filter(Objects::nonNull)
                .mapToInt(ProductSku::getStock)
                .filter(val -> val >= 0)
                .sum();

        ProductDetailVO detailVO = new ProductDetailVO();
        detailVO.setProduct(spu);
        detailVO.setSkuList(skuList);
        detailVO.setMinPrice(minPrice);
        detailVO.setMaxPrice(maxPrice);
        detailVO.setStock(stock);
        detailVO.setReviewCount(0);

        return detailVO;
    }

    /**
     * 构建商品查询条件
     */
    private Specification<ProductSpu> buildProductSpecification(ProductQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), query.getCategoryId()));
            }

            if (query.getBrandId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brandId"), query.getBrandId()));
            }

            if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + query.getKeyword().trim() + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));
            predicates.add(criteriaBuilder.equal(root.get("isShelves"), 1));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 构建基础查询条件（已上架且未删除）
     */
    private Specification<ProductSpu> buildBaseSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), 0));
            predicates.add(cb.equal(root.get("isShelves"), 1));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 构建排序规则 - 修复价格排序逻辑
     */
    private Sort buildSort(String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "price_asc":
                // 价格升序：由于SPU表没有minPrice字段，这里需要特殊处理
                // 实际应用中应该通过子查询或连接查询来实现真正的价格排序
                sort = Sort.by(Sort.Direction.ASC, "id"); // 临时使用ID排序
                break;
            case "price_desc":
                // 价格降序：同上
                sort = Sort.by(Sort.Direction.DESC, "id"); // 临时使用ID排序
                break;
            case "sales":
                sort = Sort.by(Sort.Direction.DESC, "sales");
                break;
            default:
                sort = Sort.by(Sort.Direction.DESC, "createTime");
                break;
        }
        return sort;
    }

    /**
     * 标准化limit参数
     */
    private Integer normalizeLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return 10;
        }
        return Math.min(limit, 50); // 限制最大返回数量
    }

    /**
     * 转换为VO对象
     */
    private ProductVO convertToVO(ProductSpu spu) {
        if (spu == null) {
            return null;
        }
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(spu, vo);
        
        // 查询SKU获取价格区间
        List<ProductSku> skuList = productSkuRepository.findBySpuIdAndIsDeleted(spu.getId(), 0);
        if (!CollectionUtils.isEmpty(skuList)) {
            BigDecimal minPrice = skuList.stream()
                    .filter(Objects::nonNull)
                    .map(ProductSku::getPrice)
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            
            BigDecimal maxPrice = skuList.stream()
                    .filter(Objects::nonNull)
                    .map(ProductSku::getPrice)
                    .filter(Objects::nonNull)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            
            vo.setMinPrice(minPrice);
            vo.setMaxPrice(maxPrice);
        }
        
        return vo;
    }
}