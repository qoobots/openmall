package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.User;
import com.qoobot.openmall.portal.service.UserService;
import com.qoobot.openmall.portal.vo.UserInfoVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 修改密码页
     */
    @GetMapping("/password")
    public String passwordPage() {
        return "user/password";
    }

    /**
     * 修改密码
     */
    @PostMapping("/password")
    @ResponseBody
    public Result<Void> changePassword(@RequestParam String oldPassword,
                                        @RequestParam String newPassword,
                                        @RequestParam String confirmPassword) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        if (!newPassword.equals(confirmPassword)) {
            return Result.error("两次输入的新密码不一致");
        }
        if (newPassword.length() < 6) {
            return Result.error("新密码长度不能少于6位");
        }
        try {
            userService.changePassword(currentUser.getId(), oldPassword, newPassword);
            return Result.success("密码修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 手机号绑定页
     */
    @GetMapping("/bind-mobile")
    public String bindMobilePage() {
        return "user/bind-mobile";
    }

    /**
     * 发送验证码
     */
    @PostMapping("/send-sms")
    @ResponseBody
    public Result<Void> sendSms(@RequestParam String mobile, HttpSession session) {
        if (mobile == null || !mobile.matches("^1[3-9]\\d{9}$")) {
            return Result.error("手机号格式不正确");
        }
        // TODO: 接入真实短信服务
        String code = String.format("%06d", (int) (Math.random() * 1000000));
        session.setAttribute("smsCode", code);
        session.setAttribute("smsMobile", mobile);
        session.setMaxInactiveInterval(300); // 5分钟有效
        System.out.println("【验证码】" + mobile + ": " + code);
        return Result.success("验证码已发送");
    }

    /**
     * 绑定手机号
     */
    @PostMapping("/bind-mobile")
    @ResponseBody
    public Result<Void> bindMobile(@RequestParam String mobile,
                                    @RequestParam String code,
                                    HttpSession session) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        String savedCode = (String) session.getAttribute("smsCode");
        String savedMobile = (String) session.getAttribute("smsMobile");
        if (savedCode == null || !savedCode.equals(code) || !mobile.equals(savedMobile)) {
            return Result.error("验证码错误或已过期");
        }
        currentUser.setMobile(mobile);
        userService.updateUser(currentUser);
        session.removeAttribute("smsCode");
        session.removeAttribute("smsMobile");
        return Result.success("手机号绑定成功");
    }

    /**
     * 头像上传
     */
    @PostMapping("/upload-avatar")
    @ResponseBody
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        try {
            // 校验文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只支持图片格式");
            }
            if (file.getSize() > 2 * 1024 * 1024) {
                return Result.error("头像大小不能超过2MB");
            }
            // TODO: 上传到文件服务器/OSS，这里暂时使用本地路径
            String avatarUrl = "/uploads/avatar/" + currentUser.getId() + "_" + System.currentTimeMillis() + ".jpg";
            currentUser.setAvatar(avatarUrl);
            userService.updateUser(currentUser);
            return Result.success("头像上传成功", avatarUrl);
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
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
