package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.User;
import com.qoobot.openmall.common.domain.entity.UserAddress;
import com.qoobot.openmall.portal.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收货地址控制器
 */
@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressRepository addressRepository;

    /**
     * 查询用户地址列表
     */
    @GetMapping("/list")
    @ResponseBody
    public List<UserAddress> list() {
        User user = getCurrentUser();
        return addressRepository.findByUserIdAndIsDeletedOrderByIsDefaultDescIdDesc(user.getId(), 0);
    }

    /**
     * 保存地址
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Void> save(@RequestBody UserAddress address) {
        User user = getCurrentUser();
        address.setUserId(user.getId());
        address.setCreateBy(user.getId());
        address.setUpdateBy(user.getId());

        // 如果设置为默认地址，先取消其他默认地址
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            List<UserAddress> addresses = addressRepository.findByUserIdAndIsDeletedOrderByIsDefaultDescIdDesc(user.getId(), 0);
            for (UserAddress addr : addresses) {
                if (addr.getIsDefault() == 1) {
                    addr.setIsDefault(0);
                    addressRepository.save(addr);
                }
            }
        }

        addressRepository.save(address);
        return Result.success("保存成功");
    }

    /**
     * 删除地址
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        User user = getCurrentUser();
        UserAddress address = addressRepository.findById(id).orElse(null);
        if (address != null && address.getUserId().equals(user.getId())) {
            address.setIsDeleted(1);
            addressRepository.save(address);
        }
        return Result.success("删除成功");
    }

    /**
     * 设置默认地址
     */
    @PostMapping("/setDefault/{id}")
    @ResponseBody
    public Result<Void> setDefault(@PathVariable Long id) {
        User user = getCurrentUser();

        // 取消所有默认地址
        List<UserAddress> addresses = addressRepository.findByUserIdAndIsDeletedOrderByIsDefaultDescIdDesc(user.getId(), 0);
        for (UserAddress addr : addresses) {
            if (addr.getIsDefault() == 1) {
                addr.setIsDefault(0);
                addressRepository.save(addr);
            }
        }

        // 设置新的默认地址
        UserAddress address = addressRepository.findById(id).orElse(null);
        if (address != null && address.getUserId().equals(user.getId())) {
            address.setIsDefault(1);
            addressRepository.save(address);
        }

        return Result.success("设置成功");
    }

    private com.qoobot.openmall.common.domain.entity.User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.qoobot.openmall.common.domain.entity.User) {
            return (com.qoobot.openmall.common.domain.entity.User) authentication.getPrincipal();
        }
        return null;
    }
}
