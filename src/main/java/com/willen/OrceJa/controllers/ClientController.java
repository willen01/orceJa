package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveClientDto;
import com.willen.OrceJa.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Salvar cliente",
            description = "Esse endpoint registra um novo cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "200", description = "Success"),

            }
    )
    public ResponseEntity<Void> saveClient(@Valid @RequestBody SaveClientDto clientRequest) {
        clientService.saveClient(clientRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
