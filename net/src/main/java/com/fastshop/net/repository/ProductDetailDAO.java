package com.fastshop.net.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.ProductDetail;

public interface ProductDetailDAO extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Integer productId);

    @Query("SELECT o FROM ProductDetail o WHERE o.categoryDetailId = ?1 AND o.productId = ?2")
    Optional<ProductDetail> findByCategoryDetailIdAndProductId(String category_detail_id, Integer productId);
}
