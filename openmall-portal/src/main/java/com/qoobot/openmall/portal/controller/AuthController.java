package com.qoobot.openmall.portal.controller;

import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.portal.dto.UserLoginDTO;
import com.qoobot.openmall.portal.dto.UserRegisterDTO;
import com.qoobot.openmall.portal.service.UserService;
import com.qoobot.openmall.portal.vo.UserInfoVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "auth/login";
    }

    /**
     * 注册页
     */
    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        return "auth/register";
    }

    /**
     * 用户注册
     */
    @PostMapping("/do-register")
    @ResponseBody
    public Result<Void> register(@Valid @RequestBody UserRegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功，请登录");
    }

    /**
     * 用户登录（AJAX）
     */
    @PostMapping("/login-ajax")
    @ResponseBody
    public Result<UserInfoVO> loginAjax(@Valid @RequestBody UserLoginDTO dto, HttpSession session) {
        UserInfoVO userVO = userService.login(dto);
        session.setAttribute("userId", userVO.getId());
        session.setAttribute("username", userVO.getUsername());
        session.setAttribute("user", userVO);
        return Result.success("登录成功", userVO);
    }
}
