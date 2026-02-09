package com.qoobot.openmall.portal.repository;

import com.qoobot.openmall.common.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询用户
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据手机号查询用户
     */
    Optional<User> findByMobile(String mobile);

    /**
     * 根据用户名和用户类型查询
     */
    Optional<User> findByUsernameAndUserType(String username, Integer userType);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查手机号是否存在
     */
    boolean existsByMobile(String mobile);
}
