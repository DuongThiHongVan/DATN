package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshop.net.model.Status;

public interface StatusDAO extends JpaRepository<Status, Integer>{
    
}
