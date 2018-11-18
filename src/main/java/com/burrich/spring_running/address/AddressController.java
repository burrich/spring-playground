package com.burrich.spring_running.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Integer id)  {
        return addressService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address addNewAddress(@RequestBody Address address) {
        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(@PathVariable Integer id) {
        addressService.delete(id);
    }
}
