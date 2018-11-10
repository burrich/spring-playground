package com.burrich.spring_running.address;

import java.util.Optional;

public interface AddressService {

    Iterable<Address> findAll();
    Optional<Address> findById(Integer id);
    String create(Address address);
    String update(Integer id, Address address);
    String delete(Integer id);
}
