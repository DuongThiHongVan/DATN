package com.fastshop.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CommentService;
import com.fastshop.net.service.DiscountService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.OrderDetailService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._SessionService;

@Controller
public class AdminController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _SessionService session;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OrderService orderService;
    @Autowired
    DiscountService discountService;
    @Autowired
    CommentService commentService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    NotifyService notifyService;

    @RequestMapping("/admin/home")
    public String home(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Thống kê doanh số bán hàng";
            model.addAttribute("page", "admin.home");
            model.addAttribute("title_main", title_main);
            model.addAttribute("totalRevenue", orderDetailService.getTotalRevenue());
            model.addAttribute("totalRevenueLast", orderDetailService.getTotalRevenueLast());
            model.addAttribute("totalOrder", orderDetailService.getTotalOrder());
            model.addAttribute("totalOrderLast", orderDetailService.getTotalOrderLast());
            model.addAttribute("top3Product", orderDetailService.getTop3BestSelling());

            // thông số số lượng của admin
            model.addAttribute("members", authorityService.countByRoleEqualsUser());
            model.addAttribute("memeberOrdered", orderService.getListUsernameOrdered().size());
            model.addAttribute("histories", historyService.findAll());
            model.addAttribute("voucherAll", discountService.findByAll().size());
            model.addAttribute("voucherUnexpiry", discountService.findByDiscountUnexpiry().size());
            model.addAttribute("orderAll", orderService.findAll().size());
            model.addAttribute("orderSuccess", orderService.countOrderSuccess());
            model.addAttribute("commentsAll", commentService.findAll().size());
            model.addAttribute("comments5", commentService.countByRateEquals5());

            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.sangtaocungme.com";
        }
    }

    @RequestMapping("/admin/employee")
    public String customer(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Thống kê danh sách nhân viên";
            model.addAttribute("staff", new Account());
            model.addAttribute("title_main", title_main);
            model.addAttribute("employees", authorityService.getListStaff());
            model.addAttribute("page", "admin.employee");
            model.addAttribute("histories", historyService.findAll());
            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));

            return "index";
        } catch (Exception e) {
            return "redirect:/login.sangtaocungme.com";
        }
    }


    @RequestMapping("/admin/history")
    public String history(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Lịch sử hoạt động";
            model.addAttribute("histories", historyService.findAll());
            model.addAttribute("page", "admin.history");
            model.addAttribute("title_main", title_main);
            model.addAttribute("histories", historyService.findAll());
            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));

            return "index";
        } catch (Exception e) {
            return "redirect:/login.sangtaocungme.com";
        }
    }


    @RequestMapping("/admin/help/docs")
    public String docs(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main = "Tài liệu hỗ trợ phần mềm";
        model.addAttribute("page", "admin.docs");
        model.addAttribute("title_main", title_main);
        model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));
        model.addAttribute("histories", historyService.findAll());

        return "index";
    }


    @RequestMapping("/admin/help/license")
    public String license(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main  = "Tài liệu thông tin pháp lý";
        model.addAttribute("page", "admin.license");
        model.addAttribute("title_main", title_main);
        model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));
        model.addAttribute("histories", historyService.findAll());

        return "index";
    }


    @RequestMapping("/admin/help/contact")
    public String contact(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main = "Thông tin liên hệ chúng tôi";
        model.addAttribute("page", "admin.contact");
        model.addAttribute("title_main", title_main);
        model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(authority.getAccount()));
        model.addAttribute("histories", historyService.findAll());

        return "index";
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
            auth = authorityService.findByAccount(account);
        }
        return auth;
    }
}
