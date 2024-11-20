package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.News;
import com.blog.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Custom query to find all comments by a specific news article
    List<Comment> findByNews(News news);

    // Custom query to find all comments by a specific user
    List<Comment> findByAuthor(User author);

    // Custom query to find all comments created after a specific date
    List<Comment> findByCreatedDateAfter(LocalDateTime date);
}
