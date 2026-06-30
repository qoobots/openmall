package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.ProductSpu;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.common.domain.entity.OrderMaster;
import com.qoobot.openmall.merchant.repository.ProductSpuRepository;
import com.qoobot.openmall.merchant.repository.ProductSkuRepository;
import com.qoobot.openmall.merchant.repository.OrderRepository;
import com.qoobot.openmall.merchant.service.ExportImportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportImportServiceImpl implements ExportImportService {

    private final ProductSpuRepository productSpuRepository;
    private final ProductSkuRepository productSkuRepository;
    private final OrderRepository orderRepository;

    @Override
    public void exportProducts(Long shopId, HttpServletResponse response) throws IOException {
        List<ProductSpu> products = productSpuRepository.findByShopIdAndIsDeletedOrderByCreateTimeDesc(shopId, 0);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("商品列表");

        // 表头
        Row header = sheet.createRow(0);
        String[] headers = {"商品ID", "商品名称", "分类ID", "品牌ID", "主图URL", "是否上架", "销量", "浏览量", "创建时间"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 数据
        int rowIdx = 1;
        for (ProductSpu p : products) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getProductName() != null ? p.getProductName() : "");
            row.createCell(2).setCellValue(p.getCategoryId() != null ? p.getCategoryId().toString() : "");
            row.createCell(3).setCellValue(p.getBrandId() != null ? p.getBrandId().toString() : "");
            row.createCell(4).setCellValue(p.getMainImage() != null ? p.getMainImage() : "");
            row.createCell(5).setCellValue(p.getIsShelves() != null && p.getIsShelves() == 1 ? "上架" : "下架");
            row.createCell(6).setCellValue(p.getSales() != null ? p.getSales() : 0);
            row.createCell(7).setCellValue(p.getViews() != null ? p.getViews() : 0);
            row.createCell(8).setCellValue(p.getCreateTime() != null ? p.getCreateTime().toString() : "");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("商品列表.xlsx", StandardCharsets.UTF_8).replace("+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        try (OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        }
        workbook.close();
    }

    @Override
    public int importProducts(Long shopId, MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        int importedCount = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String productName = getCellString(row, 1);
                if (productName == null || productName.isEmpty()) continue;

                ProductSpu spu = new ProductSpu();
                spu.setShopId(shopId);
                spu.setProductName(productName);
                spu.setCategoryId(parseLong(getCellString(row, 2)));
                spu.setBrandId(parseLong(getCellString(row, 3)));
                spu.setMainImage(getCellString(row, 4));
                spu.setIsShelves(1);
                spu.setCreateBy(1L);
                spu.setUpdateBy(1L);

                productSpuRepository.save(spu);
                importedCount++;
            } catch (Exception e) {
                // 跳过异常行
            }
        }

        workbook.close();
        return importedCount;
    }

    @Override
    public void exportOrders(Long shopId, HttpServletResponse response) throws IOException {
        List<OrderMaster> orders = orderRepository.findByShopId(shopId, org.springframework.data.domain.PageRequest.of(0, Integer.MAX_VALUE, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createTime")))
                .getContent();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("订单列表");

        Row header = sheet.createRow(0);
        String[] headers = {"订单ID", "订单号", "买家ID", "状态", "总金额", "运费", "优惠", "实付", "收货人", "收货电话", "收货地址", "物流公司", "物流单号", "下单时间"};
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }

        int rowIdx = 1;
        for (OrderMaster o : orders) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(o.getId());
            row.createCell(1).setCellValue(o.getOrderNo());
            row.createCell(2).setCellValue(o.getUserId());
            row.createCell(3).setCellValue(getOrderStatusText(o.getOrderStatus()));
            row.createCell(4).setCellValue(o.getTotalAmount() != null ? o.getTotalAmount().toString() : "0");
            row.createCell(5).setCellValue(o.getFreightAmount() != null ? o.getFreightAmount().toString() : "0");
            row.createCell(6).setCellValue(o.getDiscountAmount() != null ? o.getDiscountAmount().toString() : "0");
            row.createCell(7).setCellValue(o.getPayAmount() != null ? o.getPayAmount().toString() : "0");
            row.createCell(8).setCellValue(o.getConsigneeName() != null ? o.getConsigneeName() : "");
            row.createCell(9).setCellValue(o.getConsigneePhone() != null ? o.getConsigneePhone() : "");
            row.createCell(10).setCellValue(o.getConsigneeAddress() != null ? o.getConsigneeAddress() : "");
            row.createCell(11).setCellValue(o.getLogisticsCompany() != null ? o.getLogisticsCompany() : "");
            row.createCell(12).setCellValue(o.getLogisticsNo() != null ? o.getLogisticsNo() : "");
            row.createCell(13).setCellValue(o.getCreateTime() != null ? o.getCreateTime().toString() : "");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("订单列表.xlsx", StandardCharsets.UTF_8).replace("+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        try (OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        }
        workbook.close();
    }

    private String getCellString(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
        return cell.toString();
    }

    private Long parseLong(String val) {
        if (val == null || val.isEmpty()) return null;
        try { return Long.valueOf(val.trim()); } catch (NumberFormatException e) { return null; }
    }

    private String getOrderStatusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 1: return "待付款";
            case 2: return "待发货";
            case 3: return "已发货";
            case 4: return "已完成";
            case 5: return "已取消";
            case 6: return "退款中";
            case 7: return "已退款";
            default: return "未知";
        }
    }
}
