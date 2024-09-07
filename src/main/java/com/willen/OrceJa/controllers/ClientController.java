package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveClientDto;
import com.willen.OrceJa.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveClient(@RequestBody SaveClientDto clientRequest) {
        clientService.saveClient(clientRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
