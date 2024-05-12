package com.fastshop.net.controller.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Notify;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDTO;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._CookieService;

@Controller
public class ProductController {
    // public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/dist/img/products";
    public static String uploadDir = Paths.get("net", "src", "main", "resources", "static", "dist", "img", "products").toAbsolutePath().toString().replace("\\", "/");
    // public static String uploadDir = "D:/3/TTS/CVTT/FastshopHandmade/sangtaocungme/net/src/main/resources/static/dist/img/products";
    @Autowired
    _CookieService cookieService;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    NotifyService notifyService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/staff/products/add")
    public String postProAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile fileProductImage,
                             @RequestParam("imgName") String imgName) {
        try {
            Account account = accountService.findByUsername(cookieService.getValue("username"));
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setCategory(categoryService.findById(productDTO.getCategory()));
            product.setPrice(productDTO.getPrice());
            product.setNumber(productDTO.getNumber());
            product.setCreateDate(new Date());
            product.setAvailable(productDTO.getNumber() > 0);
            product.setDescribe(null);
            String imageUUID = "";
            if(!fileProductImage.isEmpty()){
                imageUUID = fileProductImage.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
                Files.write(fileNameAndPath, fileProductImage.getBytes());
            }else {
                imageUUID = imgName;
            }
            product.setImage(imageUUID);

            productService.save(product);

            Notify notify = new Notify();
            notify.setSentDate(new Date());
            notify.setAccount(account);
            notify.setStatus(true);
            notify.setTitle("Bạn đã thêm thành công sản phẩm " + productDTO.getName());
            notify.setFileName("- Tin nhắn thông báo -");
            notifyService.save(notify);

            return "redirect:/staff/products";   
        } catch (IOException e) {
//            return "redirect:/login.sangtaocungme.com";
            return "redirect:/staff/products";
        }
    }


    /**
     * this is model of authority
     * @return
     */
    @ModelAttribute("auth")
    public Authority getAuth() {
        Authority auth = null;
        String username = cookieService.getValue("username");
        if (username != null) {
            Account account = accountService.findByUsernameOrEmail(username, username);
            auth = authorityService.findByAccount(account);
        }
        return auth;
    }
}
