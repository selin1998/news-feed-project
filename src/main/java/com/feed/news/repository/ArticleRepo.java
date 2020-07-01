package com.feed.news.repository;

import com.feed.news.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepo extends JpaRepository<Article,String> {
    List<Article> findByHeaderIgnoreCaseContaining(String keyword1);
}

