package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Discount;
import java.util.Date;


public interface DiscountDAO extends JpaRepository<Discount, String>{
    List<Discount> findByDolarNotNull();
    List<Discount> findByFreeNotNull();
    List<Discount> findByDateEndAfter(Date dateEnd);
    @Query("SELECT o FROM Discount o WHERE o.free = ?1")
    List<Discount> findByFreeEqualNumber(Double free);
}
