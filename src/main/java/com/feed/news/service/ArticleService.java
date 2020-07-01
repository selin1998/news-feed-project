package com.feed.news.service;

import com.feed.news.entity.Article;
import com.feed.news.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo repo) {

        this.articleRepo= repo;
    }

    public List<Article> findArticleByKeyword(String keyword){
        return articleRepo.findByKeyword(keyword);
    }
    public List<Article> saveAll(List<Article> articles){
        return articleRepo.saveAll(articles);
    }
}
