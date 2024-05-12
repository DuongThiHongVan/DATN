package com.fastshop.net.controller.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Address;
import com.fastshop.net.model.Authority;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AddressService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service._CookieService;

@Controller
public class AddressController {
    @Autowired
    _CookieService cookie;
    @Autowired
    AddressService addressService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/user/address/update")
    public String save(@ModelAttribute("auth") Authority authority) {
        Long id =  Long.parseLong(request.getParameter("place"));
        if (id != 0) {
            addressService.update(authority.getAccount().getUsername(), id);
        }

        return "redirect:/user/address";
    }

    @RequestMapping("/user/address/add")
    public String add(@ModelAttribute("auth") Authority auth) {
        try {
            String username = auth.getAccount().getUsername();
            List<Address> list = addressService.findAllAddressByAccount(username);
            Address address = new Address();
            address.setChoose(list.size() == 0);
            address.setPlace(request.getParameter("place"));
            address.setUsername(username);
            addressService.save(address);
            return "redirect:/user/address";
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
            auth = authorityService.findByAccount(account);
        }
        return auth;
    }
}
