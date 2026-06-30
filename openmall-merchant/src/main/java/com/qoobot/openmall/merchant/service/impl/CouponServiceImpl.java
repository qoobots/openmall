package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.Coupon;
import com.qoobot.openmall.merchant.dto.CouponDTO;
import com.qoobot.openmall.merchant.dto.CouponVO;
import com.qoobot.openmall.merchant.dto.PageResult;
import com.qoobot.openmall.merchant.repository.CouponRepository;
import com.qoobot.openmall.merchant.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券Service实现
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public PageResult<CouponVO> queryPage(Pageable pageable, Long shopId) {
        // 分页查询
        Page<Coupon> page = couponRepository.findByShopId(shopId, pageable);

        // 转换为VO
        List<CouponVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(),
                pageable.getPageNumber() + 1, pageable.getPageSize());
    }

    @Override
    public CouponVO getById(Long id, Long shopId) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        // 验证店铺权限
        if (!coupon.getShopId().equals(shopId)) {
            throw new RuntimeException("无权访问该优惠券");
        }

        return convertToVO(coupon);
    }

    @Transactional
    @Override
    public Long save(CouponDTO dto, Long shopId) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(dto, coupon);
        coupon.setShopId(shopId);
        coupon.setRemainCount(dto.getTotalCount()); // 初始剩余数量等于总数量
        coupon.setStatus(1); // 默认启用
        coupon.setCreateTime(LocalDateTime.now());
        coupon.setUpdateTime(LocalDateTime.now());
        coupon.setCreateBy(shopId); // TODO: 修改为当前用户ID
        coupon.setUpdateBy(shopId); // TODO: 修改为当前用户ID
        coupon.setIsDeleted(0);
        coupon.setVersion(0);

        Coupon saved = couponRepository.save(coupon);
        return saved.getId();
    }

    @Transactional
    @Override
    public void update(CouponDTO dto, Long shopId) {
        Coupon coupon = couponRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        // 验证店铺权限
        if (!coupon.getShopId().equals(shopId)) {
            throw new RuntimeException("无权修改该优惠券");
        }

        // 更新优惠券信息
        BeanUtils.copyProperties(dto, coupon, "id", "shopId", "createTime", "createBy");
        coupon.setUpdateTime(LocalDateTime.now());
        coupon.setUpdateBy(shopId); // TODO: 修改为当前用户ID

        couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void delete(Long id, Long shopId) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("优惠券不存在"));

        // 验证店铺权限
        if (!coupon.getShopId().equals(shopId)) {
            throw new RuntimeException("无权删除该优惠券");
        }

        // 逻辑删除
        coupon.setIsDeleted(1);
        coupon.setUpdateTime(LocalDateTime.now());
        coupon.setUpdateBy(shopId); // TODO: 修改为当前用户ID

        couponRepository.save(coupon);
    }

    /**
     * 转换为VO
     */
    private CouponVO convertToVO(Coupon coupon) {
        CouponVO vo = new CouponVO();
        BeanUtils.copyProperties(coupon, vo);

        // 设置状态文本
        vo.setStatusText(getCouponStatusText(coupon));
        vo.setCouponTypeText(getCouponTypeText(coupon.getCouponType()));

        return vo;
    }

    /**
     * 获取优惠券状态文本
     */
    private String getCouponStatusText(Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartTime())) {
            return "未开始";
        } else if (now.isAfter(coupon.getEndTime())) {
            return "已结束";
        } else {
            return "进行中";
        }
    }

    /**
     * 获取优惠券类型文本
     */
    private String getCouponTypeText(Integer type) {
        switch (type) {
            case 1: return "满减券";
            case 2: return "折扣券";
            case 3: return "现金券";
            default: return "未知";
        }
    }
}
