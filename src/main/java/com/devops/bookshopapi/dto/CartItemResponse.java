package com.devops.bookshopapi.dto;


import java.math.BigDecimal;

public record CartItemResponse(Long itemId,
                               Long bookId,
                               String title,
                               Integer quantity,
                               BigDecimal unitPrice,
                               BigDecimal lineTotal) {
}

