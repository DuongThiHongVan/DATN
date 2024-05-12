package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Address;

public interface AddressService {
    void save(Address address);
    void update(String username, Long id);
    List<Address> findAllAddressByAccount(String username);
    String findByAccountWithChooseIsTrue(String username);
    Address findByUsernameAndId(String username, Long id);
}
