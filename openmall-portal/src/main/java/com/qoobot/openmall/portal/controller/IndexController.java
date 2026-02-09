package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.domain.entity.ProductSpu;
import com.qoobot.openmall.portal.service.IndexService;
import com.qoobot.openmall.portal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页控制器
 *
 * @author openmall
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ProductService productService;
    private final IndexService indexService;

    /**
     * 首页
     */
    @GetMapping("/")
    public String index(Model model) {
        // 查询轮播图
        model.addAttribute("banners", indexService.getBanners());

        // 查询推荐商品
        model.addAttribute("recommendProducts", productService.getRecommendProducts(8));

        // 查询热门商品
        model.addAttribute("hotProducts", productService.getHotProducts(8));

        // 查询新品
        model.addAttribute("newProducts", productService.getNewProducts(8));

        // 查询分类
        model.addAttribute("categories", indexService.getCategories());

        return "index/index";
    }
}
