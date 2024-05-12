package com.fastshop.net.controller.forgot;

import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.AccountDTO;
import com.fastshop.net.model.Role;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._MailService;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._SessionService;
@Controller
public class ForgorController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _SessionService session;
    @Autowired
    _MailService mailService;
    @Autowired
    _SessionService sessionService;
    @Autowired
    AuthorityService authService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    ProductService cartSevice;
    
    @RequestMapping("/forgot/access")
	 public String sendMail(Model model, @RequestParam("email") String email) {
		try {
            if (email.isEmpty()) {
                model.addAttribute("error_forgot", "(*) Vui lòng nhập email.");
                return "forgot";
            }
            else {
                Account account = accountService.findByEmail(email);
                if (account != null) {
                    cookie.add("email", email, 1*24*60);
                    mailService.send(
                        email, 
                        "Xác nhận tài khoản của bạn",
                        "<a href='http://localhost:8080/ChangeForgot'><button class='btn btn-primary'>Nhấn xác nhận</button></a>"
                    );
                }
                return "success/success";
            }
		} catch (Exception e) {
			model.addAttribute("error_forgot", "(*) Email của bạn không tồn tại.");
            return "forgot";
		}
	}

    @RequestMapping("/ChangeForgot")
	public String ChangeForgot(Model model) {
        model.addAttribute("accounts", new AccountDTO());
        model.addAttribute("account", cookie.getValue("email"));
		return "change-forgot";
	}

    @RequestMapping("/ChangeForgotPassword")
    public String uploadPassword(Model model, @ModelAttribute("accounts") AccountDTO accountDTO) {
        String message = "";
        model.addAttribute("error1", accountDTO.getNewPassword().isEmpty() ? "(*) Mật khẩu mới cần phải được nhập ..." : "");
        model.addAttribute("error2", accountDTO.getConfirmPassword().isEmpty() ? "(*) Mật khẩu Xác nhận cần được nhập ..." : !accountDTO.getNewPassword().equals(accountDTO.getConfirmPassword()) ? "(*) Không trùng mật khẩu" : "");
        String email = cookie.getValue("email");
        Account account = accountService.findByEmail(email);

        if (accountDTO.getNewPassword().isEmpty() == false && accountDTO.getConfirmPassword().isEmpty() == false && accountDTO.getNewPassword().equals(accountDTO.getConfirmPassword())) {
            try {
                account.setPassword(accountDTO.getConfirmPassword());
                accountService.save(account);
                Role role = authService.findByAccount(account).getRole();
                String path = role.getId().equals("USER") ? "redirect:/login" : "redirect:/login.sangtaocungme.com";
                cookie.remove("email");
                return path;
            } catch (Exception e) {
                message = e.getMessage();
                model.addAttribute("error2","(*) " + message);
                return "change-forgot"; 
            }
        }
        else {
            model.addAttribute("accounts", new AccountDTO());
            model.addAttribute("account", cookie.getValue("email"));
            return "change-forgot";
        }
    }
}
