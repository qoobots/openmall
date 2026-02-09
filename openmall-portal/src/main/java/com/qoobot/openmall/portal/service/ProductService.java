package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.portal.dto.ProductQuery;
import com.qoobot.openmall.portal.vo.ProductDetailVO;
import com.qoobot.openmall.portal.vo.ProductVO;

import java.util.List;

/**
 * 商品服务接口
 *
 * @author openmall
 */
public interface ProductService {

    /**
     * 分页查询商品
     */
    PageResult<ProductVO> queryPage(ProductQuery query);

    /**
     * 获取推荐商品
     */
    List<ProductVO> getRecommendProducts(Integer limit);

    /**
     * 获取热门商品
     */
    List<ProductVO> getHotProducts(Integer limit);

    /**
     * 获取新品
     */
    List<ProductVO> getNewProducts(Integer limit);

    /**
     * 获取商品详情VO
     */
    ProductDetailVO getDetailVO(Long id);
}
