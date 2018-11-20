package com.burrich.spring_running.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

// TODO: init tests (addresses fixtures)

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository repository;

    @Test
    public void findAll() {
        Address address = new Address(
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        this.entityManager.persist(address);

        address = new Address(
                "bar",
                "bar street",
                11111,
                "bar city",
                "bar state",
                "bar country"
        );
        this.entityManager.persist(address);

        List<Address> addresses = this.repository.findAll();

        assertThat(addresses.size(), equalTo(2));
        assertEquals(addresses.get(0).getName(), "foo");
        assertEquals(addresses.get(1).getName(), "bar");
    }

    @Test
    public void findById() {
        Address address = new Address(
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        this.entityManager.persist(address);

        Integer addressId = address.getId();
        Address addressFound = this.repository
                .findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));

        assertEquals(addressFound.getName(), address.getName());
    }

    @Test
    public void save() {
        Address address = new Address(
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );

        Address addressSaved = this.repository.save(address);
        assertEquals(addressSaved, address);
    }

    @Test
    public void deleteById() {
        Address address = new Address(
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        this.entityManager.persist(address);
        this.repository.deleteById(address.getId());
        
        int addressesCount = this.repository
                .findAll()
                .size();

        assertEquals(addressesCount, 0);
    }
}
