package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;
import com.fastshop.net.service.ProductDetailService;

@RestController
public class RestProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/rest/product/detail/all")
    public List<ProductDetail> getAll() {
        return productDetailService.findAll();
    }

    // get detail của product id
    @GetMapping("/rest/product/detail/{id}")
    public List<ProductDetailBackup> findByProductId(@PathVariable("id") Integer id) {
        return productDetailService.getByProductId(id);
    }

    // get detail của product detail id
    @GetMapping("/rest/productdetail/{id}")
    public ProductDetail findById(@PathVariable("id") Long id) {
        return productDetailService.findById(id);
    }
    
    // chỉ lấy thuộc tính và catgorydetail id
    @GetMapping("/rest/product/detail/map/{id}")
    public Map<String, String> getAllOfCategoryDetailId(@PathVariable("id") Integer id) {
        return productDetailService.getAllOfCategoryDetailAndInfo(id);
    };

    // kiểm tra record đó có tồn tại hay ko
    @GetMapping("/rest/product/detail/exist/{id}")
    public Boolean existAnyProductDetailByProductId(@PathVariable("id") Integer id) {
        return productDetailService.existAnyProductDetailByProductId(id);
    };

    // kiểm tra record đó có tồn tại hay ko
    @GetMapping("/rest/product/detail/{cateId}/{id}")
    public ProductDetail findByProductDetailByProductId(@PathVariable("cateId") String cateId, @PathVariable("id") Integer id) {
        return productDetailService.findByCategoryDetailIdAndProductId(cateId, id);
    };

    // thêm product detail
    @PostMapping("/rest/product/detail")
    public void create(@RequestBody ProductDetail productDetail) {
        if (productDetailService.findAll().contains(productDetail) == false) {
            productDetailService.save(productDetail);
        }
    }

    // cập nhật thông tin của product detail id
    @PutMapping("/rest/product/detail/update/{id}")
    public ProductDetail put(@PathVariable("id") Long id, @RequestBody ProductDetail productDetail) {
        productDetailService.save(productDetail);
        return productDetail;
    }

    // xoa record theo id của product detail
    @DeleteMapping("/rest/product/detail/{id}")
    public void detete(@PathVariable("id") Long id) {
        productDetailService.deleteById(id);
    }
}
