package com.burrich.spring_running.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController {

    @Autowired
    AddressServiceImpl addressService;

    @GetMapping
    public Iterable<Address> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Address> getAddressById(@PathVariable Integer id)  {
        return addressService.findById(id);
    }

    @PostMapping
    public String addNewAddress(@RequestBody Address address) {
        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public String updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public String removeAddress(@PathVariable Integer id) {
        return addressService.delete(id);
    }
}
