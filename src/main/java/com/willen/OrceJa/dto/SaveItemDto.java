package com.willen.OrceJa.dto;

import java.math.BigDecimal;

public record SaveItemDto(String title, int quantity, BigDecimal price) {
}
