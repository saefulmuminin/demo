package com.blog.demo.repository;

import com.blog.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Custom query to find a tag by name
    Optional<Tag> findByName(String name);

    // Check if a tag exists by name
    boolean existsByName(String name);
}
