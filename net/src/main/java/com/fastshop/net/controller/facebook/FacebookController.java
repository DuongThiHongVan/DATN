package com.fastshop.net.controller.facebook;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.DtoLogin;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service.UserService;

@Controller
public class FacebookController {
    @Autowired
    _CookieService cookieService;
    @Autowired
    AccountService accountService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;


    // tất cả những cái này ko cần nữa
    @RequestMapping("/facebook/login")
    public String ggLogin(Model model) {
        DtoLogin dtoLogin = new DtoLogin("", "");
        model.addAttribute("dtoLogin", dtoLogin);
        model.addAttribute("message", "");
        return "sign-in";
    }

    @RequestMapping("/facebook/login/success")
    public String success(OAuth2AuthenticationToken token) {
        boolean status = userService.loginFromOAuth2(token); 
        // tìm thấy gmail trong db nếu có thì chuyển trang chính, ngược lại lỗi
        // có 1 cách là nếu ko tìm thấy thì sẽ tạo 1 tài khoản trong db luôn nếu làm thêm chức năng này
        return (status) ? "redirect:/sangtaocungme.com" : "error/error-404";
    }

    @RequestMapping("/facebook/login/error")
    public String ggLoginError(Model model) {
        DtoLogin dtoLogin = new DtoLogin("", "");
        model.addAttribute("dtoLogin", dtoLogin);
        model.addAttribute("message", "KHÔNG TÌM THẤY TÀI KHOẢN CỦA BẠN");
        return "sign-in";
    }

    @RequestMapping("/facebook/logout")
    public String out(Model model) {
        model.addAttribute("auth", null);
        cookieService.remove("username");
        return "redirect:/sangtaocungme.com";
    }

    // @RequestMapping("/google/logout/success")
    // public String ggLogoutSuccess(Model model) {
    //     return "redirect:/";
    // }

    
}
