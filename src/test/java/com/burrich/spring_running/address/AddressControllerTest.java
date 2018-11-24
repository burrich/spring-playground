package com.burrich.spring_running.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddressService service;

    @Test
    public void getAllAddresses() throws Exception {
        List<Address> addresses = new ArrayList<>();

        Address address1 = new Address(
                0,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        addresses.add(address1);

        Address address2 = new Address(
                1,
                "bar",
                "bar street",
                11111,
                "bar city",
                "bar state",
                "bar country"
        );
        addresses.add(address2);

        given(this.service.findAll())
                .willReturn(addresses);

        this.mvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(address1.getName())))
                .andExpect(jsonPath("$[1].name", is(address2.getName())));
    }

    @Test
    public void getAddressById() throws Exception {
        Integer addressId = 0;
        Address address = new Address(
                addressId,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );

        given(this.service.findById(addressId))
                .willReturn(address);

        this.mvc.perform(get("/api/addresses/" + addressId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(addressId)));
    }

    @Test
    public void addNewAddress() throws Exception {
        Integer addressId = 0;
        Address address = new Address(
                addressId,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        String addressJson = mapper.writeValueAsString(address);

        given(this.service.create(ArgumentMatchers.any(Address.class)))
                .willReturn(address);

        this.mvc.perform(post("/api/addresses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(addressJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(addressId)));
    }

    @Test
    public void updateAddress() throws Exception {
        Integer addressId = 0;
        Address address = new Address(
                addressId,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        String addressJson = mapper.writeValueAsString(address);

        given(this.service.update(
                    eq(addressId),
                    ArgumentMatchers.any(Address.class)))
                .willReturn(address);

        this.mvc.perform(put("/api/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(addressId)));
    }

    @Test
    public void removeAddress() throws Exception {
        Integer addressId = 0;
        Address address = new Address(
                addressId,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );

        this.mvc.perform(delete("/api/addresses/" + addressId))
                .andExpect(status().isNoContent());
    }
}
