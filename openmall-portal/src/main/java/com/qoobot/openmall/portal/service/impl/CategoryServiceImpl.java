package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.domain.entity.Category;
import com.qoobot.openmall.portal.repository.CategoryRepository;
import com.qoobot.openmall.portal.service.CategoryService;
import com.qoobot.openmall.portal.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类服务实现
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryVO> listTopCategories() {
        List<Category> categories = categoryRepository.findByLevelAndIsDeletedAndIsShowOrderBySortOrder(1, 0, 1);
        return categories.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryVO> listByParentId(Long parentId) {
        List<Category> categories = categoryRepository.findByParentIdAndIsDeletedOrderBySortOrder(parentId, 0);
        return categories.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<CategoryVO> getCategoryTree() {
        // 查询所有一级分类
        List<Category> topCategories = categoryRepository.findByLevelAndIsDeletedAndIsShowOrderBySortOrder(1, 0, 1);
        List<CategoryVO> tree = new ArrayList<>();

        for (Category category : topCategories) {
            CategoryVO vo = convertToVO(category);
            // 查询子分类
            List<Category> children = categoryRepository.findByParentIdAndIsDeletedOrderBySortOrder(category.getId(), 0);
            vo.setChildren(children.stream()
                    .map(this::convertToVO)
                    .collect(Collectors.toList()));
            tree.add(vo);
        }

        return tree;
    }

    private CategoryVO convertToVO(Category category) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }
}
