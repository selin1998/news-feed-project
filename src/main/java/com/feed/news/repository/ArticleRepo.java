package com.feed.news.repository;

import com.feed.news.crawler.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article,String> {

}

