package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.util.List;

/**
 * 分页结果
 */
@Data
public class PageResult<T> {
    private List<T> content;
    private long totalElements;
    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean first;
    private boolean last;

    public PageResult(List<T> content, long totalElements, int pageNum, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.first = pageNum == 1;
        this.last = pageNum >= totalPages;
    }

    public PageResult() {
    }
}
