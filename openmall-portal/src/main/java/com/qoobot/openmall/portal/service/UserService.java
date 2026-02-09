package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.domain.entity.User;
import com.qoobot.openmall.portal.dto.UserLoginDTO;
import com.qoobot.openmall.portal.dto.UserRegisterDTO;
import com.qoobot.openmall.portal.vo.UserInfoVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    void register(UserRegisterDTO dto);

    /**
     * 用户登录
     */
    UserInfoVO login(UserLoginDTO dto);

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 检查用户名是否存在
     */
    boolean checkUsernameExists(String username);
}
