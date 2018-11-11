package com.burrich.spring_running.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: error handling (create) => model validation
// TODO: exist method (refactoring)

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Iterable<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Integer id) {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Integer id, Address address) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException(id);
        }

        address.setId(id);
        return addressRepository.save(address);
    }

    @Override
    public void delete(Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException(id);
        }

        addressRepository.deleteById(id);
    }
}
