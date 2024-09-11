package com.willen.OrceJa.dto;

import com.willen.OrceJa.enums.ProjectStatus;

import java.util.UUID;

public record ListAllProjectsDto(
        UUID clientId,
        String owner,
        String projectName,
        String description,
        ProjectStatus status
) {
}
