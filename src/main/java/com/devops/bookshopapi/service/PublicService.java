package com.devops.bookshopapi.service;

import com.devops.bookshopapi.dto.BookResponse;
import com.devops.bookshopapi.dto.CategoryResponse;
import com.devops.bookshopapi.entity.Book;
import com.devops.bookshopapi.exception.NotFoundException;
import com.devops.bookshopapi.repository.BookRepository;
import com.devops.bookshopapi.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PublicService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public PublicService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<CategoryResponse> categories() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    public Page<BookResponse> books(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size)).map(this::toBookResponse);
    }

    public BookResponse bookDetail(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
        return toBookResponse(book);
    }

    private BookResponse toBookResponse(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getStock(),
                b.getDescription(), b.getCategory().getId(), b.getCategory().getName());
    }
}
