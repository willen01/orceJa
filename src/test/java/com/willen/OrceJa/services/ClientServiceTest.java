package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveClientDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    @Nested
    class registerUser {

        @Test
        @DisplayName("Deve cadastrar um cliente com sucesso")
        void cadastraClienteComSucess() {
            // Arrange
            Client client = new Client(
                    UUID.randomUUID(),
                    "Jhon Doe",
                    "jhon.doe@gmail.com",
                    "(92)965898264",
                    Instant.now()
            );

            doReturn(client).when(clientRepository).save(clientCaptor.capture());
            // act
            SaveClientDto input = new SaveClientDto("Jhon Doe",
                    "jhon.doe@gmail.com",
                    "(92)965898264");

            clientService.saveClient(input);

            //assert
            Client clientCaptured = clientCaptor.getValue();
            assertEquals(input.name(), clientCaptured.getName());
            assertEquals(input.email(), clientCaptured.getEmail());
            assertEquals(input.phone(), clientCaptured.getPhone());

            verify(clientRepository, times(1)).save(any());
        }
    }
}