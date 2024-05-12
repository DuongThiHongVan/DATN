package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.History;
import com.fastshop.net.service.HistoryService;

@RestController
public class RestHistoryController {
    @Autowired
    HistoryService historyService;

    @GetMapping("/rest/history/all")
    public List<History> findAll() {
        return historyService.findAll();
    }

    @DeleteMapping("/rest/history/{id}")
    public void findById(@PathVariable("id") Long id) {
        historyService.deleteById(id);
    }
}
