package com.burrich.spring_running.address;

public interface AddressService {

    Iterable<Address> findAll();
    Address findById(Integer id);
    Address create(Address address);
    Address update(Integer id, Address address);
    void delete(Integer id);
}
