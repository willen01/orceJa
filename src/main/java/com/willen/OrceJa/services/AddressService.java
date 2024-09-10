package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveAddressDto;
import com.willen.OrceJa.entities.Address;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.exceptions.DefaultAddressRemovalException;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.AddressRepository;
import com.willen.OrceJa.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final ClientRepository clientRepository;

    public AddressService(AddressRepository addressRepository, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }

    public void saveAddress(SaveAddressDto saveAddressDto) {
        Client client = clientRepository.findById(UUID.fromString(saveAddressDto.clientId()))
                .orElseThrow(() -> new ObjectNotFoundException("user not registered"));

        Address address = new Address();
        address.setClient(client);
        address.setCity(saveAddressDto.city());
        address.setStreet(saveAddressDto.street());
        address.setState(saveAddressDto.state());
        address.setNumber(saveAddressDto.number());
        address.setDefault(saveAddressDto.isDefault());

        addressRepository.save(address);
    }


    public void removeAddress(String addressId) {
        Address address = addressRepository
                .findById(UUID.fromString(addressId))
                .orElseThrow(() -> new ObjectNotFoundException("Address with id" + addressId + " not found"));

        boolean isAddressDefault = address.isDefault();

        if (isAddressDefault) {
            throw new DefaultAddressRemovalException("Cannot remove the address because it is set as the default. " +
                    "Please choose a different address as default before removal.");
        }

        addressRepository.deleteById(UUID.fromString(addressId));
    }

}
