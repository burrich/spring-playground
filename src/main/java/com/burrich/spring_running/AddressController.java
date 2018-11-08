package com.burrich.spring_running;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Address> getAddressById(@PathVariable Integer id)  {
        return addressRepository.findById(id);
    }

    @PostMapping
    public String addNewAddress(@RequestBody Address address) {
        addressRepository.save(address);
        return "Address saved";
    }

    @PutMapping("/{id}")
    public String updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        if (!addressRepository.existsById(id)) {
            return "Id doesn't exist";
        }

        address.setId(id);
        addressRepository.save(address);

        return "Address updated";
    }

    @DeleteMapping("/{id}")
    public String removeAddress(@PathVariable Integer id) {
        addressRepository.deleteById(id);
        return "Address Deleted";
    }
}
