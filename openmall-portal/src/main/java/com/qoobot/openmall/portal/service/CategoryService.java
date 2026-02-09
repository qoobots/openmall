package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.domain.entity.Category;
import com.qoobot.openmall.portal.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     */
    List<CategoryVO> listTopCategories();

    /**
     * 根据父分类ID查询子分类
     */
    List<CategoryVO> listByParentId(Long parentId);

    /**
     * 根据ID查询分类
     */
    Category getCategoryById(Long id);

    /**
     * 查询分类树
     */
    List<CategoryVO> getCategoryTree();
}
