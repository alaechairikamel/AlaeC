package com.devops.bookshopapi.controller;

import com.devops.bookshopapi.dto.BookResponse;
import com.devops.bookshopapi.dto.CreateBookRequest;
import com.devops.bookshopapi.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse createBook(@RequestBody @Valid CreateBookRequest request) {
        return adminService.createBook(request);
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        adminService.deleteBook(id);
    }
}