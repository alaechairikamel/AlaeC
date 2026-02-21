package com.devops.bookshopapi.controller;

import com.devops.bookshopapi.dto.BookResponse;
import com.devops.bookshopapi.dto.CategoryResponse;
import com.devops.bookshopapi.service.PublicService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/categories")
    public List<CategoryResponse> categories() {
        return publicService.categories();
    }

    @GetMapping("/books")
    public Page<BookResponse> books(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return publicService.books(page, size);
    }

    @GetMapping("/books/{id}")
    public BookResponse bookDetail(@PathVariable Long id) {
        return publicService.bookDetail(id);
    }
}
