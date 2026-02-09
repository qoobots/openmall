package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.User;
import com.qoobot.openmall.portal.service.UserService;
import com.qoobot.openmall.portal.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户中心首页
     */
    @GetMapping("/index")
    public String index(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        return "user/index";
    }

    /**
     * 个人资料页
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("user", currentUser);
        return "user/profile";
    }

    /**
     * 收货地址页
     */
    @GetMapping("/address")
    public String address(Model model) {
        return "user/address";
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody User user) {
        User currentUser = getCurrentUser();
        user.setId(currentUser.getId());
        userService.updateUser(user);
        return Result.success("更新成功");
    }

    /**
     * 获取当前登录用户
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
