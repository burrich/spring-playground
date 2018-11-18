package com.burrich.spring_running.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService service;

    @Test
    public void getAllAddresses() throws Exception {
        List<Address> addresses = new ArrayList<>();

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

        given(this.service.findAll()).willReturn(addresses);

        this.mvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(addresses.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(addresses.get(1).getName())));
    }
}
