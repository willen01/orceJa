package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveAddressDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.exceptions.ObjectNotFoundException;
import com.willen.OrceJa.repositories.AddressRepository;
import com.willen.OrceJa.repositories.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AddressService addressService;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @Nested
    class SaveAddress {

        @Test
        @DisplayName("Deve ser possivel cadastrar um endereço com sucesso")
        void shouldBeRegisterAddressWithSuccess() {
            Client client = new Client(UUID.randomUUID(),
                    "Jhon Doe", "jhon.doe@provider.com",
                    "92988658758",
                    Instant.now());

            doReturn(Optional.of(client)).when(clientRepository).findById(uuidCaptor.capture());

            SaveAddressDto addressRequest = new SaveAddressDto(
                    client.getClientId().toString(),
                    "João Pessoa",
                    "Rua das flores",
                    "PE",
                    30,
                    true
            );


            addressService.saveAddress(addressRequest);

            assertEquals(client.getClientId(), uuidCaptor.getValue());
            verify(addressRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("Deve falhar ao tentar cadadastrar um endereço para um cliente não cadastrado")
        void shouldBeThrowWhenRegisterAddressWithUnregisteredClient() {
            UUID clientId = UUID.randomUUID();
            doThrow(ObjectNotFoundException.class).when(clientRepository).findById(uuidCaptor.capture());

            SaveAddressDto addressRequest = new SaveAddressDto(
                    clientId.toString(),
                    "João Pessoa",
                    "Rua das flores",
                    "PE",
                    30,
                    true
            );

            assertThrows(ObjectNotFoundException.class, () -> addressService.saveAddress(addressRequest));
            assertEquals(clientId, uuidCaptor.getValue());
            verify(addressRepository, times(0)).save(any());
        }
    }

}