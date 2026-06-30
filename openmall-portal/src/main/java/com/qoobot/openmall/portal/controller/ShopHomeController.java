package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.domain.entity.Shop;
import com.qoobot.openmall.common.domain.entity.ShopPageModule;
import com.qoobot.openmall.portal.repository.ShopPageModuleRepository;
import com.qoobot.openmall.portal.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 店铺前台控制器
 */
@Controller
@RequiredArgsConstructor
public class ShopHomeController {

    private final ShopRepository shopRepository;
    private final ShopPageModuleRepository shopPageModuleRepository;

    /**
     * 店铺首页
     */
    @GetMapping("/shop/{id}")
    public String home(@PathVariable Long id, Model model) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop == null) {
            return "redirect:/";
        }

        List<ShopPageModule> modules = shopPageModuleRepository
                .findByShopIdAndIsEnabledAndIsDeletedOrderBySortOrderAsc(id, 1, 0);

        model.addAttribute("shop", shop);
        model.addAttribute("modules", modules);
        return "shop/home";
    }
}
