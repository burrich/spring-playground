package running;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @PostMapping
    public String addNewAddress(@RequestBody Address address) {
        addressRepository.save(address);
        return "Address saved";
    }
}
