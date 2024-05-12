package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.CommentDAO;
import com.fastshop.net.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentDAO commentDAO;

    @Override
    public Page<Comment> getCommentsByProduct(Product product, Pageable pageable) {
        return commentDAO.findByProduct(product, pageable);
    }

    @Override
    public Page<Comment> getCommentsByAccount(Account account, Pageable pageable) {
        return commentDAO.findByAccount(account, pageable);
    }

    @Override
    public Long countByProduct(Product product) {
        return commentDAO.countByProduct(product);
    }

    @Override
    public List<Integer> getMaxStar(Product product) {
        int max;
        try {
            max = commentDAO.getMaxStar(product);
        } catch (Exception e) {
            max = 1;
        }
        List<Integer> number = new ArrayList<>();
        if (max == 5) {
            for (int i = 0; i < 5; i++) {
                number.add(1);
            }
        }
        else {
            for (int i = 0; i < max; i++) {
                number.add(1);
            }

            for (int i = 0; i < 5 - max; i++) {
                number.add(0);
            }
        }

        return number;
    }

    @Override
    public List<Comment> findAll() {
        return commentDAO.findAll();
    }

    @Override
    public Comment findById(Integer id) {
        return commentDAO.findById(id).get();
    }

    @Override
    public List<Comment> findByAccount(Account account) {
        return commentDAO.findByAccount(account);
    }

    @Override
    public void save(Comment account) {
        commentDAO.save(account);
    }

    @Override
    public void deleteById(Integer id) {
        commentDAO.deleteById(id);
    }

    @Override
    public List<Comment> listCommentByUser(String username) {
        return commentDAO.listCommentByUser(username);
    }

    @Override
    public Integer countByRateEquals5() {
        return commentDAO.countByRateEquals5();
    }
}
