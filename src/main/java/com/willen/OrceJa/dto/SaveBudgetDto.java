package com.willen.OrceJa.dto;

import com.willen.OrceJa.enums.BudgetStatus;

import java.util.List;
import java.util.UUID;

public record SaveBudgetDto (UUID projectId, String details, List<SaveItemDto> items, BudgetStatus status){
}
