package com.willen.OrceJa.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record SaveClientDto(
        @NotEmpty(message = "The field NAME is mandatory")
        String name,
        @NotEmpty(message = "The field EMAIL is mandatory")
        String email,
        @NotEmpty(message = "The field PHONE is mandatory")
        @Length(min = 11, message = "The field PHONE is must be 11 characters")
        String phone) {
}
