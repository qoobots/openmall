package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户收货地址Repository
 */
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    /**
     * 根据用户ID查询地址列表
     */
    List<UserAddress> findByUserIdAndIsDeletedOrderByIsDefaultDescIdDesc(Long userId, Integer isDeleted);

    /**
     * 查询用户默认地址
     */
    Optional<UserAddress> findByUserIdAndIsDefaultAndIsDeleted(Long userId, Integer isDefault, Integer isDeleted);
}
