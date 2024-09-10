package com.willen.OrceJa.dto;

import com.willen.OrceJa.enums.ProjectStatus;

import java.util.UUID;

public record ProjectListDto(
        UUID projectId,
        String name,
        String description,
        ProjectStatus status
) {
}
