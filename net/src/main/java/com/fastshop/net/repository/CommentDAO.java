package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;

public interface CommentDAO extends JpaRepository<Comment, Integer>{
    Page<Comment> findByProduct(Product product, Pageable pageable);
    Page<Comment> findByAccount(Account account, Pageable pageable);
    Long countByProduct(Product product);

    @Query("SELECT MAX(o.rate) FROM Comment o WHERE o.product = ?1")
    Integer getMaxStar(Product product);

    List<Comment> findByAccount(Account account);

    @Query("SELECT o FROM Comment o WHERE o.account.username = ?1")
    List<Comment> listCommentByUser(String username);

        @Query("SELECT COUNT(o) FROM Comment o WHERE o.rate = 5")
    Integer countByRateEquals5();
}
