package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.ShopPageModule;
import com.qoobot.openmall.merchant.service.ShopDecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺装修控制器
 */
@Controller
@RequestMapping("/decoration")
@RequiredArgsConstructor
public class ShopDecorationController {

    private final ShopDecorationService decorationService;

    @GetMapping
    public String list(Model model) {
        List<ShopPageModule> modules = decorationService.getModules(getShopId());
        model.addAttribute("modules", modules);
        return "decoration/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("entity", new ShopPageModule());
        return "decoration/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("entity", decorationService.getById(id, getShopId()));
        return "decoration/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public Result<Long> save(@RequestBody ShopPageModule entity) {
        try {
            Long id = decorationService.save(entity, getShopId());
            return Result.success("保存成功", id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ShopPageModule entity) {
        try {
            decorationService.update(entity, getShopId());
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        try {
            decorationService.delete(id, getShopId());
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/sort/{id}")
    @ResponseBody
    public Result<Void> sort(@PathVariable Long id, @RequestParam Integer sortOrder) {
        try {
            decorationService.updateSortOrder(id, sortOrder, getShopId());
            return Result.success("排序更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private Long getShopId() {
        return 1L;
    }
}
