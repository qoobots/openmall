package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类VO
 */
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String categoryName;
    private Integer level;
    private String icon;
    private Integer sortOrder;
    private Integer isShow;

    /**
     * 子分类列表
     */
    private List<CategoryVO> children;
}
