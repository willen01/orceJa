package com.willen.OrceJa.dto;

import java.util.UUID;

public record SaveAddressDto(
        UUID clientId,
        String city,
        String street,
        String state,
        int number,
        boolean isDefault) {
}
