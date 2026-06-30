package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.PromotionFullReduction;
import com.qoobot.openmall.common.domain.entity.PromotionDiscount;
import com.qoobot.openmall.common.domain.entity.PromotionSeckill;
import com.qoobot.openmall.merchant.service.PromotionFullReductionService;
import com.qoobot.openmall.merchant.service.PromotionDiscountService;
import com.qoobot.openmall.merchant.service.PromotionSeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 营销活动控制器
 */
@Controller
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionFullReductionService fullReductionService;
    private final PromotionDiscountService discountService;
    private final PromotionSeckillService seckillService;

    // ==================== 满减活动 ====================

    @GetMapping("/full-reduction")
    public String fullReductionList(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "20") Integer pageSize,
                                     Model model) {
        PageResult<PromotionFullReduction> page = fullReductionService.queryPage(getShopId(), pageNum, pageSize);
        model.addAttribute("page", page);
        return "promotion/full-reduction-list";
    }

    @GetMapping("/full-reduction/add")
    public String fullReductionAdd(Model model) {
        model.addAttribute("entity", new PromotionFullReduction());
        return "promotion/full-reduction-edit";
    }

    @GetMapping("/full-reduction/edit/{id}")
    public String fullReductionEdit(@PathVariable Long id, Model model) {
        model.addAttribute("entity", fullReductionService.getById(id, getShopId()));
        return "promotion/full-reduction-edit";
    }

    @PostMapping("/full-reduction/save")
    @ResponseBody
    public Result<Long> fullReductionSave(@RequestBody PromotionFullReduction entity) {
        try {
            Long id = fullReductionService.save(entity, getShopId());
            return Result.success("保存成功", id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/full-reduction/update")
    @ResponseBody
    public Result<Void> fullReductionUpdate(@RequestBody PromotionFullReduction entity) {
        try {
            fullReductionService.update(entity, getShopId());
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/full-reduction/delete/{id}")
    @ResponseBody
    public Result<Void> fullReductionDelete(@PathVariable Long id) {
        try {
            fullReductionService.delete(id, getShopId());
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // ==================== 限时折扣 ====================

    @GetMapping("/discount")
    public String discountList(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "20") Integer pageSize,
                                Model model) {
        PageResult<PromotionDiscount> page = discountService.queryPage(getShopId(), pageNum, pageSize);
        model.addAttribute("page", page);
        return "promotion/discount-list";
    }

    @GetMapping("/discount/add")
    public String discountAdd(Model model) {
        model.addAttribute("entity", new PromotionDiscount());
        return "promotion/discount-edit";
    }

    @GetMapping("/discount/edit/{id}")
    public String discountEdit(@PathVariable Long id, Model model) {
        model.addAttribute("entity", discountService.getById(id, getShopId()));
        return "promotion/discount-edit";
    }

    @PostMapping("/discount/save")
    @ResponseBody
    public Result<Long> discountSave(@RequestBody PromotionDiscount entity) {
        try {
            Long id = discountService.save(entity, getShopId());
            return Result.success("保存成功", id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/discount/update")
    @ResponseBody
    public Result<Void> discountUpdate(@RequestBody PromotionDiscount entity) {
        try {
            discountService.update(entity, getShopId());
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/discount/delete/{id}")
    @ResponseBody
    public Result<Void> discountDelete(@PathVariable Long id) {
        try {
            discountService.delete(id, getShopId());
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // ==================== 秒杀活动 ====================

    @GetMapping("/seckill")
    public String seckillList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "20") Integer pageSize,
                               Model model) {
        PageResult<PromotionSeckill> page = seckillService.queryPage(getShopId(), pageNum, pageSize);
        model.addAttribute("page", page);
        return "promotion/seckill-list";
    }

    @GetMapping("/seckill/add")
    public String seckillAdd(Model model) {
        model.addAttribute("entity", new PromotionSeckill());
        return "promotion/seckill-edit";
    }

    @GetMapping("/seckill/edit/{id}")
    public String seckillEdit(@PathVariable Long id, Model model) {
        model.addAttribute("entity", seckillService.getById(id, getShopId()));
        return "promotion/seckill-edit";
    }

    @PostMapping("/seckill/save")
    @ResponseBody
    public Result<Long> seckillSave(@RequestBody PromotionSeckill entity) {
        try {
            Long id = seckillService.save(entity, getShopId());
            return Result.success("保存成功", id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/seckill/update")
    @ResponseBody
    public Result<Void> seckillUpdate(@RequestBody PromotionSeckill entity) {
        try {
            seckillService.update(entity, getShopId());
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/seckill/delete/{id}")
    @ResponseBody
    public Result<Void> seckillDelete(@PathVariable Long id) {
        try {
            seckillService.delete(id, getShopId());
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    private Long getShopId() {
        return 1L;
    }
}
