package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveAddressDto;
import com.willen.OrceJa.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveAddress(@Valid @RequestBody SaveAddressDto request) {
        addressService.saveAddress(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{addressId}")
    public ResponseEntity<Void> removeAddress(@PathVariable("addressId") String addressId) {
        addressService.removeAddress(addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
