package com.willen.OrceJa.controllers;

import com.willen.OrceJa.dto.SaveAddressDto;
import com.willen.OrceJa.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(
            summary = "Cadastrar endereço",
            description = "Este endpoint cadastra um novo endereço associado à um cliente já cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
            }

    )
    @PostMapping("/save")
    public ResponseEntity<Void> saveAddress(@Valid @RequestBody SaveAddressDto request) {
        addressService.saveAddress(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Deletar endereço",
            description = "Este endpoint deleta endereço com base no id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
            }

    )
    @DeleteMapping("/remove/{addressId}")
    public ResponseEntity<Void> removeAddress(
            @Parameter(
                    in = ParameterIn.PATH,
                    name = "addressId",
                    required = true,
                    description = "id do endereço",
                    schema = @Schema(
                            type = "string",
                            format = "uuid"
                    ))
            @PathVariable("addressId") String addressId) {
        addressService.removeAddress(addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
