package com.burrich.spring_running.address;

import java.util.List;

public interface AddressService {

    List<Address> findAll();
    Address findById(Integer id);
    Address create(Address address);
    Address update(Integer id, Address address);
    void delete(Integer id);
}
