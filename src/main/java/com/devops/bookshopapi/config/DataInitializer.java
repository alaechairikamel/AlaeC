package com.devops.bookshopapi.config;

import com.devops.bookshopapi.entity.*;
import com.devops.bookshopapi.repository.BookRepository;
import com.devops.bookshopapi.repository.CategoryRepository;
import com.devops.bookshopapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(CategoryRepository categoryRepository,
                               BookRepository bookRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (categoryRepository.count() == 0) {
                Category tech = new Category();
                tech.setName("Technology");
                Category fiction = new Category();
                fiction.setName("Fiction");
                categoryRepository.save(tech);
                categoryRepository.save(fiction);

                Book cleanCode = new Book();
                cleanCode.setTitle("Clean Code");
                cleanCode.setAuthor("Robert C. Martin");
                cleanCode.setPrice(new BigDecimal("39.99"));
                cleanCode.setStock(20);
                cleanCode.setDescription("Software craftsmanship principles");
                cleanCode.setCategory(tech);
                bookRepository.save(cleanCode);

                Book dune = new Book();
                dune.setTitle("Dune");
                dune.setAuthor("Frank Herbert");
                dune.setPrice(new BigDecimal("24.50"));
                dune.setStock(15);
                dune.setDescription("Sci-fi classic");
                dune.setCategory(fiction);
                bookRepository.save(dune);
            }

            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("user@bookshop.local");
                user.setPasswordHash(passwordEncoder.encode("password"));
                user.setRole(Role.USER);
                userRepository.save(user);

                User admin = new User();
                admin.setEmail("admin@bookshop.local");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
