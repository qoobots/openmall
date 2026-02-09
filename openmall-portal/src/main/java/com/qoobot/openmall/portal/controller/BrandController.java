package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.domain.entity.Brand;
import com.qoobot.openmall.portal.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌控制器
 */
@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandRepository brandRepository;

    /**
     * 查询所有品牌
     */
    @GetMapping("/list")
    @ResponseBody
    public List<Brand> list() {
        return brandRepository.findByIsDeletedAndIsShowOrderBySortOrder(0, 1);
    }

    /**
     * 根据ID查询品牌
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Brand getById(@PathVariable Long id) {
        return brandRepository.findByIdAndIsDeleted(id, 0);
    }
}
