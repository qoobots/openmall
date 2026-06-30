package com.qoobot.openmall.merchant.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Excel导入导出服务
 */
public interface ExportImportService {

    /**
     * 导出商品数据为Excel
     */
    void exportProducts(Long shopId, HttpServletResponse response) throws IOException;

    /**
     * 导入商品数据
     */
    int importProducts(Long shopId, MultipartFile file) throws IOException;

    /**
     * 导出订单数据为Excel
     */
    void exportOrders(Long shopId, HttpServletResponse response) throws IOException;
}
