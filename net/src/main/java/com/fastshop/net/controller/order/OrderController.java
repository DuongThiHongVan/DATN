package com.fastshop.net.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Order;
import com.fastshop.net.repository.OrderDetailDAO;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.StatusService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._GetListFile;
import com.fastshop.net.service._MailService;
import com.fastshop.net.utils.ReadFile;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service.ProductDetailService;

@Controller
public class OrderController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _MailService mailService;
    @Autowired
    _GetListFile getListFile;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProductService productSevice;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryDetailService categoryDetailService;
    @Autowired
    ProductDetailService productDetailService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    StatusService statusService;
    @Autowired
    NotifyService notifyService;

    // gửi gmail loại html
    @RequestMapping("/user/order/accept")
    public String order(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            String email = auth.getAccount().getEmail();
            String subject = "Hóa đơn Fastshop của " + auth.getAccount().getFullname() + " (" + java.sql.Date.valueOf(LocalDate.now()).toString() +")";
            String body = new ReadFile().readFile("http://localhost:8080/user/bill");
            mailService.send(email, subject, body);
            System.out.println();
            System.out.println(body);
            return "redirect:/user/myorder";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }


    // xem mục quản lý hóa đơn của user
    @RequestMapping("/user/order/{username}/{id}")
    public String getAll(Model model, @ModelAttribute("auth") Authority auth, @PathVariable("id") Integer id, @PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        List<Order> orders = orderService.findByStatusAndAccount(id, account);
        try {
            model.addAttribute("page", "user.myorder");
            model.addAttribute("title_main", "Shop sáng  tạo cùng mẹ Quản lý hóa đơn");
            model.addAttribute("orders", orders);
            model.addAttribute("status", id);
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));
            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(auth.getAccount()));

            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }


    // cập nhật trạng thái hàng của user
    // id là id của đơn hàng
    @RequestMapping("/user/order/update/{orderid}")
    public String update(@ModelAttribute("auth") Authority auth, @PathVariable("orderid") Long id) {
        Order order = orderService.findById(id);
        order.setStatus(statusService.findById(-1));
        orderService.save(order);
        return "redirect:/user/myorder";
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
