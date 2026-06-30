package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.merchant.dto.*;
import com.qoobot.openmall.merchant.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 商品列表页
     */
    @GetMapping("/list")
    public String list(@RequestParam(required = false) Long categoryId,
                      @RequestParam(required = false) Integer status,
                      @RequestParam(required = false) String keyword,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "20") Integer pageSize,
                      Model model) {
        // 构建查询条件
        ProductQuery query = new ProductQuery();
        query.setCategoryId(categoryId);
        query.setStatus(status);
        query.setKeyword(keyword);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        // 分页查询
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        PageResult<ProductVO> pageResult = productService.queryPage(query, getShopId());

        // 设置模型数据
        model.addAttribute("page", pageResult);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);

        return "product/list";
    }

    /**
     * 商品编辑页
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ProductDTO product = productService.getById(id, getShopId());
        model.addAttribute("product", product);
        return "product/edit";
    }

    /**
     * 新增商品页
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", null);
        return "product/edit";
    }

    /**
     * 保存商品
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Long> save(@RequestBody ProductDTO dto) {
        try {
            Long id = productService.save(dto, getShopId());
            return Result.success(id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     */
    @PutMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ProductDTO dto) {
        try {
            productService.update(dto, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        try {
            productService.delete(id, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改上架状态
     */
    @PostMapping("/changeShelves/{id}/{status}")
    @ResponseBody
    public Result<Void> changeShelves(@PathVariable Long id, @PathVariable Integer status) {
        try {
            productService.changeShelvesStatus(id, status, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量上架
     */
    @PostMapping("/batchShelves")
    @ResponseBody
    public Result<Void> batchShelves(@RequestParam String productIds) {
        try {
            productService.batchShelves(productIds, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量下架
     */
    @PostMapping("/batchUnshelves")
    @ResponseBody
    public Result<Void> batchUnshelves(@RequestParam String productIds) {
        try {
            productService.batchUnshelves(productIds, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestParam String productIds) {
        try {
            productService.batchDelete(productIds, getShopId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取当前店铺ID
     * TODO: 从Session或SecurityContext中获取
     */
    private Long getShopId() {
        // 临时返回1L，实际应从Session或SecurityContext中获取
        return 1L;
    }
}
