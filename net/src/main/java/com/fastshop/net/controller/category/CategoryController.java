package com.fastshop.net.controller.category;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Category;
import com.fastshop.net.model.Notify;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service._CookieService;

@Controller
public class CategoryController {
    @Autowired
    _CookieService cookie;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    NotifyService notifyService;

    @RequestMapping("/category/add")
    public String add(Category category) {
        try {
            category.setStatus(true);
            categoryService.save(category);
            Account account = accountService.findByUsername(cookie.getValue("username"));

            Notify notify = new Notify();
            notify.setAccount(account);
            notify.setFileName("-Tin nhắn thông báo-");
            notify.setSentDate(new Date());
            notify.setStatus(true);
            notify.setTitle("Bạn đã thêm thành công một thể loại mặt hàng mới là " + category.getName());
            notifyService.save(notify);

            return "redirect:/staff/category/stock";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.sangtaocungme.com";
        }
    }

    @RequestMapping("/category/update/avaiable/{id}")
    public String update(@PathVariable("id") String id, @RequestParam("avaiable") Boolean avaiable, @RequestParam("focus") String focus) {
        categoryService.updateProductAvaiableFromCategory(id, avaiable);
        Category category = categoryService.findById(id);
        Account account = accountService.findByUsername(cookie.getValue("username"));
        Notify notify = new Notify();
        notify.setAccount(account);
        notify.setFileName("-Tin nhắn thông báo-");
        notify.setSentDate(new Date());
        notify.setStatus(true);
        notify.setTitle("Bạn đã thay đổi loại hàng " + category.getName() + " thành " + (avaiable?"Bán lại":"Dừng bán"));
        notifyService.save(notify);
        return "redirect:/staff/category/" + focus; 
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
