package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.History;

public interface HistoryService {
    void save(History history);
    void deleteById(Long id);
    History findById(Long id);
    List<History> findByTitle(String title);
    List<History> findAll();
}
