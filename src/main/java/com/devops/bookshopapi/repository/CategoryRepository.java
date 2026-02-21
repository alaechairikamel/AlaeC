package com.devops.bookshopapi.repository;

import com.devops.bookshopapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
