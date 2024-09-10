package com.willen.OrceJa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SaveAddressDto(
        @NotEmpty(message = "the field CLIENTID is mandatory")
        String clientId,

        @NotEmpty(message = "the field CITY is mandatory")
        String city,

        @NotEmpty(message = "the field STREET is mandatory")
        String street,

        @NotEmpty(message = "the field STATE is mandatory")
        @Size(min = 2, message = "the field STATE must have at least 2 characters")
        String state,

        int number,

        boolean isDefault) {
}
