package com.devops.bookshopapi.service;


import com.devops.bookshopapi.dto.*;
import com.devops.bookshopapi.entity.Book;
import com.devops.bookshopapi.entity.CartItem;
import com.devops.bookshopapi.entity.User;
import com.devops.bookshopapi.exception.BadRequestException;
import com.devops.bookshopapi.exception.NotFoundException;
import com.devops.bookshopapi.repository.BookRepository;
import com.devops.bookshopapi.repository.CartItemRepository;
import com.devops.bookshopapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public CartResponse getCart(String email) {
        User user = loadUser(email);
        return buildCart(user);
    }

    @Transactional
    public CartResponse addItem(String email, AddCartItemRequest request) {
        User user = loadUser(email);
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));
        if (book.getStock() < request.quantity()) {
            throw new BadRequestException("Insufficient stock");
        }

        CartItem item = cartItemRepository.findByUserAndBook_Id(user, book.getId()).orElseGet(CartItem::new);
        item.setUser(user);
        item.setBook(book);
        int newQuantity = (item.getQuantity() == null ? 0 : item.getQuantity()) + request.quantity();
        if (newQuantity > book.getStock()) {
            throw new BadRequestException("Requested quantity exceeds stock");
        }
        item.setQuantity(newQuantity);
        item.setUnitPrice(book.getPrice());
        cartItemRepository.save(item);

        return buildCart(user);
    }

    @Transactional
    public CartResponse updateItem(String email, Long itemId, UpdateCartItemRequest request) {
        User user = loadUser(email);
        CartItem item = cartItemRepository.findById(itemId)
                .filter(cartItem -> cartItem.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        if (request.quantity() > item.getBook().getStock()) {
            throw new BadRequestException("Requested quantity exceeds stock");
        }

        item.setQuantity(request.quantity());
        cartItemRepository.save(item);
        return buildCart(user);
    }

    @Transactional
    public void deleteItem(String email, Long itemId) {
        User user = loadUser(email);
        CartItem item = cartItemRepository.findById(itemId)
                .filter(cartItem -> cartItem.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Cart item not found"));
        cartItemRepository.delete(item);
    }

    private CartResponse buildCart(User user) {
        List<CartItemResponse> items = cartItemRepository.findByUser(user).stream().map(item -> {
            BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            return new CartItemResponse(item.getId(), item.getBook().getId(), item.getBook().getTitle(),
                    item.getQuantity(), item.getUnitPrice(), lineTotal);
        }).toList();

        BigDecimal total = items.stream().map(CartItemResponse::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(items, total);
    }

    private User loadUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

