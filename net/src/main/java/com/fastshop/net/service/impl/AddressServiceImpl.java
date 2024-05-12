package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Address;
import com.fastshop.net.repository.AddressDAO;
import com.fastshop.net.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDAO addressDAO;

    @Override
    public List<Address> findAllAddressByAccount(String username) {
        return addressDAO.findAllAddressByAccount(username);
    }

    @Override
    public String findByAccountWithChooseIsTrue(String username) {
        try {
            return addressDAO.findByAccountWithChooseIsTrue(username).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Address address) {
        addressDAO.save(address);
    }

    @Override
    public void update(String username, Long id) {
        List<Address> list = addressDAO.findAllAddressByAccount(username);
        for (Address address : list) {
            address.setChoose( (address.getId() == id) );
            addressDAO.save(address);
        }
    }

    @Override
    public Address findByUsernameAndId(String username, Long id) {
        return addressDAO.findByUsernameAndId(username, id).get();
    }
    
}
