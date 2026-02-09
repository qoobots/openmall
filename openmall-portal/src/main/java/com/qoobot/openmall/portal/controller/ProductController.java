package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.portal.dto.ProductQuery;
import com.qoobot.openmall.portal.vo.ProductDetailVO;
import com.qoobot.openmall.portal.vo.ProductVO;
import com.qoobot.openmall.portal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品控制器
 *
 * @author openmall
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 商品列表页
     */
    @GetMapping("/product/list")
    public String list(@RequestParam(required = false) Long categoryId,
                      @RequestParam(required = false) Long brandId,
                      @RequestParam(required = false) String keyword,
                      @RequestParam(required = false) String sortBy,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "20") Integer pageSize,
                      Model model) {

        ProductQuery query = new ProductQuery();
        query.setCategoryId(categoryId);
        query.setBrandId(brandId);
        query.setKeyword(keyword);
        query.setSortBy(sortBy);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        PageResult<ProductVO> pageResult = productService.queryPage(query);
        model.addAttribute("page", pageResult);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);

        return "product/list";
    }

    /**
     * 商品详情页
     */
    @GetMapping("/product/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ProductDetailVO detailVO = productService.getDetailVO(id);
        model.addAttribute("detailVO", detailVO);
        model.addAttribute("product", detailVO.getProduct());
        model.addAttribute("skuList", detailVO.getSkuList());
        model.addAttribute("saleAttributes", detailVO.getSaleAttributes());
        model.addAttribute("minPrice", detailVO.getMinPrice());
        model.addAttribute("maxPrice", detailVO.getMaxPrice());
        model.addAttribute("stock", detailVO.getStock());
        model.addAttribute("reviewCount", detailVO.getReviewCount());

        return "product/detail";
    }
}
