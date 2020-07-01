package com.feed.news.repository;

import com.feed.news.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepo extends JpaRepository<Article,String> {
//article.article_date=:keyword
    @Query(value="SELECT * FROM article  WHERE article.header like %:keyword%",nativeQuery=true)
    List<Article> findByKeyword(@Param("keyword")String keyword);
}

