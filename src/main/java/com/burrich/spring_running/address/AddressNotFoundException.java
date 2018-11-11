package com.burrich.spring_running.address;

public class AddressNotFoundException extends RuntimeException {

    AddressNotFoundException(Integer id) {
        super("Could not find address " + id);
    }
}
