package com.willen.OrceJa.dto;

import com.willen.OrceJa.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;

public record SaveProjectDto(
        @NotBlank(message = "The field CLIENTID is mandatory")
        String clientId,

        @NotBlank(message = "The field NAME is mandatory")
        String name,

        @NotBlank(message = "The field DESCRIPTIO is mandatory")
        String description,

        ProjectStatus status) {
}
