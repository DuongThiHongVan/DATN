package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer>{
    List<Product> findByCategory(Category category);
    boolean existsById(Integer id);

    @Query("SELECT o FROM Product o WHERE o.name LIKE %?1%")
    List<Product> findByKeywordName(String kw);
    /**
     *  SELECT * FROM products
     *  INNER JOIN categories ON categories.id = products.categoryid
     *  INNER JOIN comments ON comments.productid = products.id
     *  WHERE rate <= 4 AND categories.id LIKE '%%' AND products.price BETWEEN 0 and 200
     */
    @Query("SELECT o FROM Product o WHERE o.category.id LIKE %?1% AND (o.price BETWEEN ?2 AND ?3)")
    List<Product> findByFilter(String cateId, Double priceFrom, Double priceTo);
}
