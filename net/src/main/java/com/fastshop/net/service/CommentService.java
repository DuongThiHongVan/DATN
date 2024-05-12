package com.fastshop.net.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;

public interface CommentService {
    Page<Comment> getCommentsByProduct(Product product, Pageable pageable);
    Page<Comment> getCommentsByAccount(Account account, Pageable pageable);
    Long countByProduct(Product product);
    List<Integer> getMaxStar(Product product);
    List<Comment> findAll();
    Comment findById(Integer id);
    List<Comment> findByAccount(Account account);
    void save(Comment account);
    void deleteById(Integer id);
    Integer countByRateEquals5();
    List<Comment> listCommentByUser(String username);
}
