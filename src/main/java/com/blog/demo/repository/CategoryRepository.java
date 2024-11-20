package com.blog.demo.repository;

import com.blog.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Custom query to find a category by name
    Optional<Category> findByName(String name);

    // Check if a category exists by name
    boolean existsByName(String name);
}
