package com.feed.news.service;

import com.feed.news.entity.Article;
import com.feed.news.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo repo) {

        this.articleRepo= repo;
    }

    public List<Article> findArticleByKeyword(String keyword){
        return articleRepo.findByHeaderIgnoreCaseContainingOrContentIgnoreCaseContaining( keyword,keyword);
    }
    public List<Article> saveAll(List<Article> articles){
        return articleRepo.saveAll(articles);
    }

    public List<Article>findArticleByDate(String keyword){
        LocalDate date = LocalDate.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return articleRepo.findByDateEquals(date);
    }
}
