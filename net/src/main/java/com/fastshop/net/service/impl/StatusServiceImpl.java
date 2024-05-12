package com.fastshop.net.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Status;
import com.fastshop.net.repository.StatusDAO;
import com.fastshop.net.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService{
    @Autowired
    StatusDAO statusDAO;

    @Override
    public Status findById(Integer id) {
        return statusDAO.findById(id).get();
    }
    
}
