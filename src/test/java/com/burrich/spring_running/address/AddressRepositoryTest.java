package com.burrich.spring_running.address;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository repository;

    private List<Address> addresses = new ArrayList<>();

    @Before
    public void init() {
        Address address = new Address(
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        addresses.add(address);

        address = new Address(
                "bar",
                "bar street",
                11111,
                "bar city",
                "bar state",
                "bar country"
        );
        addresses.add(address);

        addresses.forEach(addr -> entityManager.persist(addr));
    }

    @After
    public void clean() {
        addresses.clear();
        entityManager.clear();
    }

    @Test
    public void findAll() {
        List<Address> addressesFound = repository.findAll();

        assertThat(addressesFound.size(), equalTo(addresses.size()));
        assertEquals(addressesFound.get(0).getName(), addresses.get(0).getName());
        assertEquals(addressesFound.get(1).getName(), addresses.get(1).getName());
    }

    @Test
    public void findById() {
        Address address = addresses.get(0);
        Integer addressId = address.getId();

        Address addressFound = repository
                .findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));

        assertEquals(addressFound.getName(), address.getName());
    }

    @Test
    public void save() {
        Address address = new Address(
                "random",
                "random street",
                22222,
                "random city",
                "random state",
                "random country"
        );
        addresses.add(address);

        Address addressSaved = repository.save(address);
        int addressesFoundCount = repository.findAll().size();

        assertEquals(addressesFoundCount, addresses.size());
        assertEquals(addressSaved, address);
    }

    @Test
    public void deleteById() {
        int addressListId = 0;
        Integer addressEmId = addresses.get(addressListId).getId();

        repository.deleteById(addressEmId);
        addresses.remove(addressListId);
        int addressesFoundCount = repository.findAll().size();

        assertEquals(addressesFoundCount, addresses.size());
    }
}
