package com.fastshop.net.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Address;

public interface AddressDAO extends JpaRepository<Address, Long>{
    @Query("SELECT o FROM Address o WHERE o.username = ?1")
    List<Address> findAllAddressByAccount(String username);

    @Query("SELECT o.place FROM Address o WHERE o.username = ?1 AND o.choose = true")
    Optional<String> findByAccountWithChooseIsTrue(String username);

    @Query("SELECT o FROM Address o WHERE o.username = ?1 AND o.id = ?2")
    Optional<Address> findByUsernameAndId(String username, Long id);
}
