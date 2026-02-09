package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.portal.vo.BannerVO;
import com.qoobot.openmall.portal.vo.CategoryVO;

import java.util.List;

/**
 * 首页服务接口
 */
public interface IndexService {

    /**
     * 获取轮播图列表
     */
    List<BannerVO> getBanners();

    /**
     * 获取分类列表
     */
    List<CategoryVO> getCategories();
}
