package com.burrich.spring_running.address;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Override
    List<Address> findAll();
}
