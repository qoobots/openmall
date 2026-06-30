package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_menu")
public class SysMenu extends BaseEntity {

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "menu_name", nullable = false, length = 50)
    private String menuName;

    @Column(name = "menu_path", length = 200)
    private String menuPath;

    @Column(name = "menu_icon", length = 100)
    private String menuIcon;

    @Column(name = "permission", length = 200)
    private String permission;

    @Column(name = "menu_type", nullable = false)
    private Integer menuType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_visible")
    private Integer isVisible;

    @Transient
    private List<SysMenu> children = new ArrayList<>();
}
