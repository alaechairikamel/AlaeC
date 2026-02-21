package com.devops.bookshopapi.controller;


import com.devops.bookshopapi.dto.*;
import com.devops.bookshopapi.service.CartService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartResponse getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCart(userDetails.getUsername());
    }

    @PostMapping("/items")
    public CartResponse addItem(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody @Valid AddCartItemRequest request) {
        return cartService.addItem(userDetails.getUsername(), request);
    }

    @PutMapping("/items/{itemId}")
    public CartResponse updateItem(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable Long itemId,
                                   @RequestBody @Valid UpdateCartItemRequest request) {
        return cartService.updateItem(userDetails.getUsername(), itemId, request);
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@AuthenticationPrincipal UserDetails userDetails,
                           @PathVariable Long itemId) {
        cartService.deleteItem(userDetails.getUsername(), itemId);
    }
}
