package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.domain.entity.Category;
import com.qoobot.openmall.portal.service.CategoryService;
import com.qoobot.openmall.portal.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 */
@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 查询所有一级分类
     */
    @GetMapping("/list")
    @ResponseBody
    public List<CategoryVO> list() {
        return categoryService.listTopCategories();
    }

    /**
     * 根据父分类ID查询子分类
     */
    @GetMapping("/children/{parentId}")
    @ResponseBody
    public List<CategoryVO> children(@PathVariable Long parentId) {
        return categoryService.listByParentId(parentId);
    }

    /**
     * 查询分类树
     */
    @GetMapping("/tree")
    @ResponseBody
    public List<CategoryVO> tree() {
        return categoryService.getCategoryTree();
    }

    /**
     * 根据ID查询分类
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Category getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
