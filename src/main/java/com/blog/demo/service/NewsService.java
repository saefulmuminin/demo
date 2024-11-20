package com.blog.demo.service;

import com.blog.demo.model.News;
import com.blog.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public News createNews(News news) {
        news.setPublishedDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    public News updateNews(Long id, News newsDetails) {
        return newsRepository.findById(id).map(news -> {
            news.setTitle(newsDetails.getTitle());
            news.setContent(newsDetails.getContent());
            news.setAuthor(newsDetails.getAuthor());
            news.setCategory(newsDetails.getCategory());
            news.setTags(newsDetails.getTags());
            return newsRepository.save(news);
        }).orElseThrow(() -> new RuntimeException("News not found"));
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
