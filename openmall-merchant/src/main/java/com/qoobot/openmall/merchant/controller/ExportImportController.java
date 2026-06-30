package com.qoobot.openmall.merchant.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.merchant.service.ExportImportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Excel导入导出控制器
 */
@Controller
@RequestMapping("/data")
@RequiredArgsConstructor
public class ExportImportController {

    private final ExportImportService exportImportService;

    /**
     * 导入导出页面
     */
    @GetMapping("/import-export")
    public String index() {
        return "data/import-export";
    }

    /**
     * 导出商品Excel
     */
    @GetMapping("/export-products")
    public void exportProducts(HttpServletResponse response) throws IOException {
        exportImportService.exportProducts(getShopId(), response);
    }

    /**
     * 导出订单Excel
     */
    @GetMapping("/export-orders")
    public void exportOrders(HttpServletResponse response) throws IOException {
        exportImportService.exportOrders(getShopId(), response);
    }

    /**
     * 导入商品Excel
     */
    @PostMapping("/import-products")
    @ResponseBody
    public Result<Integer> importProducts(@RequestParam("file") MultipartFile file) {
        try {
            int count = exportImportService.importProducts(getShopId(), file);
            return Result.success("成功导入" + count + "件商品", count);
        } catch (IOException e) {
            return Result.error("导入失败: " + e.getMessage());
        }
    }

    private Long getShopId() {
        return 1L;
    }
}
