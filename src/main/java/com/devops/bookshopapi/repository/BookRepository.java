package com.devops.bookshopapi.repository;

import com.devops.bookshopapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
