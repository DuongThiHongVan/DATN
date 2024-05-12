package com.fastshop.net.controller.employee;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.History;
import com.fastshop.net.model.Notify;
import com.fastshop.net.model.Role;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.RoleService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.utils.FormatDate;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmployeeController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/files";

    @Autowired
    _CookieService cookie;
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    ProductService productService;
    @Autowired
    NotifyService notifyService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    HistoryService historyService;


    @RequestMapping("/account/add")
    public String add(Account account, @ModelAttribute("auth") Authority auth) {
        try {
            // save account
            accountService.save(account);

            // save auth
            Role role = roleService.findById("STAFF");
            Authority authority = new Authority();
            authority.setAccount(account);
            authority.setRole(role);
            authorityService.save(authority);

            Notify notify = new Notify();
            notify.setAccount(auth.getAccount());
            notify.setFileName("- Tin nhắn thông báo -");
            notify.setSentDate(new Date());
            notify.setStatus(true);
            notify.setTitle("Bạn đã thêm thành công nhân viên ID ( "  + account.getUsername() + " )");
            notifyService.save(notify);

            History history = new History();
            String username = cookie.getValue("username");
            Account account1 = accountService.findByUsername(username);
            history.setAccount(account1);
            history.setTitle("Bạn đã thêm thành công nhân viên ID ( "  + account1.getUsername() + " )");
            history.setSchedual(new Date());
            history.setLink(null);
            history.setStatus(true);
            historyService.save(history);

            return "redirect:/admin/employee";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/error-duplicate";
        }
    }


    @RequestMapping("/account/active/{username}")
    public String delete(@PathVariable("username") String username, @ModelAttribute("auth") Authority auth) {
        Account account = accountService.findById(username);
        if (account.getActive() != null) {
            account.setActive( !account.getActive() );
            accountService.save(account);

            Notify notify = new Notify();
            notify.setAccount(auth.getAccount());
            notify.setFileName("- Tin nhắn thông báo -");
            notify.setSentDate(new Date());
            notify.setStatus(true);
            notify.setTitle("Bạn đã thay đổi trạng thái nhân viên " + account.getFullname() + " thành " + (!account.getActive() ? "Dừng làm việc" : "Đang làm việc"));
            notifyService.save(notify);

            History history = new History();
            history.setAccount(auth.getAccount());
            history.setTitle("Bạn đã thay đổi trạng thái nhân viên " + account.getFullname() + " thành " + (!account.getActive() ? "Dừng làm việc" : "Đang làm việc"));
            history.setSchedual(new Date());
            history.setLink(null);
            history.setStatus(true);
            historyService.save(history);
        }
        return "redirect:/admin/employee";
    }


    @RequestMapping("/account/search")
    public String name(Model model, @ModelAttribute("auth") Authority auth, String keyword) {
        if (keyword.trim().equalsIgnoreCase("") || keyword == null) {
            return "redirect:/admin/employee";
        }
        else {
            model.addAttribute("staff", new Account());
            model.addAttribute("title_main", "Admin - Thống kê danh sách nhân viên");
            model.addAttribute("employees", authorityService.findByKeyword(keyword.trim()));
            model.addAttribute("page", "admin.employee");
            model.addAttribute("count_notify", notifyService.findAllByAccAndNowAndStatusTrueOrderBy(auth.getAccount()));
            return "index";
        }
    }

    @RequestMapping("/notify/add")
    public String sentPDF(Model model, @ModelAttribute("auth") Authority auth, 
                                       @RequestParam("fileNames") MultipartFile multipartFile,
                                       @RequestParam("txtFileList") String txtFileList) {
        try {
            // this is notify for staff
            Notify notify = new Notify();
            notify.setAccount(auth.getAccount());
            notify.setStatus(true);
            notify.setSentDate(new Date());
            notify.setTitle("Đã gửi báo cáo thành công " + FormatDate.parse());
            String imageUUID = "";
            if(!multipartFile.isEmpty()){
                imageUUID = multipartFile.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
                Files.write(fileNameAndPath, multipartFile.getBytes());
            }else {
                imageUUID = txtFileList;
            }

            notify.setFileName(imageUUID);
            notifyService.save(notify);


            Notify notifyAdmin = new Notify();
            notifyAdmin.setAccount(accountService.findById("khannit159"));
            notifyAdmin.setStatus(true);
            notifyAdmin.setSentDate(new Date());
            notifyAdmin.setTitle("Nhân viên " + auth.getAccount().getFullname() + " đã gửi báo cáo " + FormatDate.parse());
            notifyAdmin.setFileName("PDF File");
            notifyService.save(notifyAdmin);

            History history = new History();
            history.setAccount(auth.getAccount());
            history.setTitle("Nhân viên " + auth.getAccount().getFullname() + " đã gửi báo cáo " + FormatDate.parse());
            history.setSchedual(new Date());
            history.setLink("http://localhost:8080/files/" + imageUUID);
            history.setStatus(true);
            historyService.save(history);

            return "redirect:/staff/report";
        } catch (Exception e) {
            return "redirect:/login.sangtaocungme.com";
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