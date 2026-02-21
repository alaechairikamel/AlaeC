package com.devops.bookshopapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateBookRequest(@NotBlank String title,
                                @NotBlank String author,
                                @NotNull @DecimalMin("0.0") BigDecimal price,
                                @NotNull @Min(0) Integer stock,
                                String description,
                                @NotNull Long categoryId) {
}
