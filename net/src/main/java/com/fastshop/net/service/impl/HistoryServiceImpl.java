package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.History;
import com.fastshop.net.repository.HistoryDAO;
import com.fastshop.net.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService{
    @Autowired
    HistoryDAO historyDAO;

    @Override
    public void save(History history) {
        historyDAO.save(history);
    }

    @Override
    public void deleteById(Long id) {
        historyDAO.deleteById(id);
    }

    @Override
    public History findById(Long id) {
        return historyDAO.findById(id).get();
    }

    @Override
    public List<History> findByTitle(String title) {
        return historyDAO.findByTitle(title);
    }

    @Override
    public List<History> findAll() {
        return historyDAO.findAll();
    }
}
