package com.blog.demo.repository;

import com.blog.demo.model.News;
import com.blog.demo.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // Custom query to find news by title
    List<News> findByTitleContaining(String title);

    // Custom query to find all news by a specific category
    List<News> findByCategory(Category category);

    // Custom query to find news by author
    List<News> findByAuthor(String author);
    
    // Custom query to find news published after a certain date
    List<News> findByPublishedDateAfter(LocalDateTime date);
}
