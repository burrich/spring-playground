package com.burrich.spring_running.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Iterable<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public String create(Address address) {
        addressRepository.save(address);
        return "Address saved";
    }

    @Override
    public String update(Integer id, Address address) {
        if (!addressRepository.existsById(id)) {
            return "Id doesn't exist";
        }

        address.setId(id);
        addressRepository.save(address);

        return "Address updated";
    }

    @Override
    public String delete(Integer id) {
        addressRepository.deleteById(id);
        return "Address Deleted";
    }
}
