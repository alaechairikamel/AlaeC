package com.devops.bookshopapi.service;

import com.devops.bookshopapi.dto.BookResponse;
import com.devops.bookshopapi.dto.CreateBookRequest;
import com.devops.bookshopapi.entity.Book;
import com.devops.bookshopapi.entity.Category;
import com.devops.bookshopapi.repository.BookRepository;
import com.devops.bookshopapi.repository.CategoryRepository;
import com.devops.bookshopapi.exception.NotFoundException;
import com.devops.bookshopapi.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        if (request.stock() < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPrice(request.price());
        book.setStock(request.stock());
        book.setDescription(request.description());
        book.setCategory(category);

        Book saved = bookRepository.save(book);
        return new BookResponse(saved.getId(), saved.getTitle(), saved.getAuthor(), saved.getPrice(), saved.getStock(),
                saved.getDescription(), saved.getCategory().getId(), saved.getCategory().getName());
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}