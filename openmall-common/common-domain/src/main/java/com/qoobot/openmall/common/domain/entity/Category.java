package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品分类实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "parent_id")
    private Long parentId = 0L;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_show", nullable = false)
    private Integer isShow = 1;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> children;
}
