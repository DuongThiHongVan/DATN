package com.fastshop.net.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.DtoLogin;
import com.fastshop.net.model.MailInfo;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._MailService;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._SessionService;

@Controller
public class WebMainController {
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
    @Autowired
    NotifyService notifyService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * this is link for empty and go head /sangtaocungme.com
     * @return
     */
    @RequestMapping("/")
    public String main() {
        return "redirect:/sangtaocungme.com";
    }


    /**
     * this is link for main page index
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/sangtaocungme.com")
    public String index(Model model, @ModelAttribute("auth") Authority auth) throws IOException {
        try {
            String title_main = "Shop sáng tạo cùng mẹ - Nơi mua sắm những món đồ handmade đẹp và ý nghĩa";
            int number_hint_keyword = 5;
            Resource[] resources_hot = applicationContext.getResources("classpath*:/static/hot/*");
            Resource[] resources_dis = applicationContext.getResources("classpath*:/static/dist/img/discount/*");
            model.addAttribute("files", resources_hot);
            model.addAttribute("discount", resources_dis);
            model.addAttribute("products", cartSevice.findAll());
            model.addAttribute("title_main", title_main);
            model.addAttribute("hints", categoryService.getOneProductEachCategories(number_hint_keyword));

            // thêm history
            if (auth != null) {
                if (auth.getRole().getId().equals("ADMIN")) {
                    return "redirect:/admin/home"; 
                }
                else if (auth.getRole().getId().equals("STAFF")) {
                    return "redirect:/staff/home";
                }
            }
            return "index";
        } catch (NullPointerException e) {
            return "error/error-500";
        }
    }


    /**
     * this ls login for user from form
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String signIn(Model model) {
        DtoLogin dtoLogin = new DtoLogin("", "");
        model.addAttribute("dtoLogin", dtoLogin);
        model.addAttribute("message", "");
        return "sign-in";
    }


    /**
     * this ls login for admin & staff from form
     * @param model
     * @return
     */
    @RequestMapping("/login.sangtaocungme.com")
    public String employee(Model model) {
        DtoLogin dtoLogin = new DtoLogin("", "");
        model.addAttribute("dtoLogin", dtoLogin);
        model.addAttribute("message", "");
        return "login_staff_admin";
    }


    /**
     * this is sign up for admin & user from form
     * @param model
     * @return
     */
    @RequestMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("account", new Account());
        return "sign-up";
    }


    /**
     * this is log out user if user login from form
     * @param model
     * @return
     */
    @RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("auth", null);
        cookie.remove("username");
        return "redirect:/sangtaocungme.com";
    }


    /**
     * this is log out admin & staff if user login from form
     * @param model
     * @return
     */
    @RequestMapping("/company/logout")
    public String logoutCopany(Model model) {
        model.addAttribute("auth", null);
        cookie.remove("username");
        return "redirect:/login.sangtaocungme.com";
    }


    /**
     * this is forget for all admin & user
     * @param model
     * @return
     */
	@RequestMapping("/forgot")
	public String forgot(Model model) {
		model.addAttribute("accounts", new MailInfo());
        model.addAttribute("error_forgot", "");
		return "forgot";
	}


    /**
     * this is profile
     * @param model
     * @return
     */
    @RequestMapping("/profile")
    public String profile(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            String username = auth.getAccount().getUsername();
            Account item = accountService.findById(username);
            model.addAttribute("_", auth.getAccount());  // thêm để khi cố ý đổi link thì tự động vô login
            model.addAttribute("page", "profile.home");
            model.addAttribute("title_main", "Shop sáng  tạo cùng mẹ Hồ sơ cá nhân");
            model.addAttribute("item", item);
            model.addAttribute("error1", "");
            model.addAttribute("error2", "");
            model.addAttribute("error3", "");
            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(auth.getAccount()));

            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }


    /**
     * this is model of authority
     * @return
     */
    @ModelAttribute("auth")
    public Authority getAuth() {
        Authority auth = null;
        String username = cookie.getValue("username");
        if (username != null) {
            Account account = accountService.findByUsernameOrEmail(username, username);
            auth = authService.findByAccount(account);
        }
        return auth;
    }

    @ModelAttribute("page")
    public String getPage() {
        String page = "";
        String username = cookie.getValue("username");
        if (username != null) {
            Account account = accountService.findByUsernameOrEmail(username, username);
            Authority authority = authService.findByAccount(account);
            page = (authority.getRole().getId().equals("ADMIN")) ? "admin.home" : 
                   (authority.getRole().getId().equals("STAFF")) ? "staff.home" : "";
        }
        return page;
    }
}
