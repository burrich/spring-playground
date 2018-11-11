package com.burrich.spring_running.address;

import java.util.Optional;

public interface AddressService {

    Iterable<Address> findAll();
    Optional<Address> findById(Integer id);
    Address create(Address address);
    Address update(Integer id, Address address);
    void delete(Integer id);
}
