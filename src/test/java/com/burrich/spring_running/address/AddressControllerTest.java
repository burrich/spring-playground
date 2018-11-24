package com.burrich.spring_running.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
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

    private List<Address> addresses = new ArrayList<>();

    @Before
    public void init() {
        Address address = new Address(
                0,
                "foo",
                "foo street",
                00000,
                "foo city",
                "foo state",
                "foo country"
        );
        addresses.add(address);

        address = new Address(
                1,
                "bar",
                "bar street",
                11111,
                "bar city",
                "bar state",
                "bar country"
        );
        addresses.add(address);
    }

    @After
    public void clean() {
        addresses.clear();
    }

    @Test
    public void getAllAddresses() throws Exception {
        given(service.findAll())
                .willReturn(addresses);

        mvc.perform(get("/api/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(addresses.get(0).getId())))
                .andExpect(jsonPath("$[1].id", is(addresses.get(1).getId())));
    }

    @Test
    public void getAddressById() throws Exception {
        Integer addressId = 0;

        given(service.findById(addressId))
                .willReturn(addresses.get(addressId));

        mvc.perform(get("/api/addresses/" + addressId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(addressId)));
    }

    @Test
    public void addNewAddress() throws Exception {
        Integer addressId = addresses.size();
        Address address = new Address(
                addressId,
                "random",
                "random street",
                22222,
                "random city",
                "random state",
                "random country"
        );
        String addressJson = mapper.writeValueAsString(address);

        given(service.create(ArgumentMatchers.any(Address.class)))
                .willReturn(address);

        mvc.perform(post("/api/addresses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(addressJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(addressId)));
    }

    @Test
    public void updateAddress() throws Exception {
        Integer addressId = 0;
        Address address = addresses.get(0);
        address.setName("foo updated");

        String addressJson = mapper.writeValueAsString(address);

        given(service.update(
                    eq(addressId),
                    ArgumentMatchers.any(Address.class)))
                .willReturn(address);

        mvc.perform(put("/api/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(addresses.get(addressId).getName())));
    }

    @Test
    public void removeAddress() throws Exception {
        Integer addressId = 0;

        mvc.perform(delete("/api/addresses/" + addressId))
                .andExpect(status().isNoContent());
    }
}
