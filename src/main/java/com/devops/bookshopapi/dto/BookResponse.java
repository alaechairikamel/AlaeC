package com.devops.bookshopapi.dto;

import java.math.BigDecimal;

public record BookResponse(Long id,
                           String title,
                           String author,
                           BigDecimal price,
                           Integer stock,
                           String description,
                           Long categoryId,
                           String categoryName) {
}
