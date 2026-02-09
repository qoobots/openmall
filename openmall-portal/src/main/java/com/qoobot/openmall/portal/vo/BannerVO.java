package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图VO
 */
@Data
public class BannerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String imageUrl;
    private String linkUrl;
    private String title;
    private Integer sortOrder;
}
