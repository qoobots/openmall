package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.portal.service.CategoryService;
import com.qoobot.openmall.portal.service.IndexService;
import com.qoobot.openmall.portal.vo.BannerVO;
import com.qoobot.openmall.portal.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页服务实现
 */
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final CategoryService categoryService;

    @Override
    public List<BannerVO> getBanners() {
        // TODO: 从数据库查询轮播图，暂时返回模拟数据
        List<BannerVO> banners = new ArrayList<>();
        
        BannerVO banner1 = new BannerVO();
        banner1.setId(1L);
        banner1.setImageUrl("/images/banner1.jpg");
        banner1.setLinkUrl("/product/list");
        banner1.setTitle("限时特惠");
        banner1.setSortOrder(1);
        banners.add(banner1);
        
        BannerVO banner2 = new BannerVO();
        banner2.setId(2L);
        banner2.setImageUrl("/images/banner2.jpg");
        banner2.setLinkUrl("/product/list");
        banner2.setTitle("新品上市");
        banner2.setSortOrder(2);
        banners.add(banner2);
        
        return banners;
    }

    @Override
    public List<CategoryVO> getCategories() {
        return categoryService.listTopCategories();
    }
}
