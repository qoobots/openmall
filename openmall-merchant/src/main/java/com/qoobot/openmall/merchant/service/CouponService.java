package com.qoobot.openmall.merchant.service;

import com.qoobot.openmall.merchant.dto.CouponDTO;
import com.qoobot.openmall.merchant.dto.CouponVO;
import com.qoobot.openmall.merchant.dto.PageResult;
import org.springframework.data.domain.Pageable;

/**
 * 优惠券Service
 */
public interface CouponService {

    /**
     * 分页查询优惠券
     */
    PageResult<CouponVO> queryPage(Pageable pageable, Long shopId);

    /**
     * 根据ID查询优惠券
     */
    CouponVO getById(Long id, Long shopId);

    /**
     * 保存优惠券
     */
    Long save(CouponDTO dto, Long shopId);

    /**
     * 更新优惠券
     */
    void update(CouponDTO dto, Long shopId);

    /**
     * 删除优惠券
     */
    void delete(Long id, Long shopId);
}
