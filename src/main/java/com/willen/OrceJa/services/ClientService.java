package com.willen.OrceJa.services;

import com.willen.OrceJa.dto.SaveClientDto;
import com.willen.OrceJa.entities.Client;
import com.willen.OrceJa.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(SaveClientDto clientRequest) {
        Client client = new Client();

        client.setName(clientRequest.name());
        client.setEmail(clientRequest.email());
        client.setPhone(clientRequest.phone());

        clientRepository.save(client);
    }
}
