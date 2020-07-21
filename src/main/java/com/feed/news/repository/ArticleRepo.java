package com.feed.news.repository;

import com.feed.news.crawler.Website;
import com.feed.news.entity.db.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article,String> {

    // (a^b^c)&d=(a&d)^(b&d)^(c&d) -> (header^content^siteEquals)&(siteIn) : for search bar
    Page<Article> findAllByHeaderIgnoreCaseContainingAndSiteInOrContentIgnoreCaseContainingAndSiteInOrSiteIgnoreCaseAndSiteInOrderByDateDesc(String keyword1, List<Website> sites1, String keyword2, List<Website> sites2, Website keyword3 , List<Website> sites3, Pageable page);

    Page<Article> findBySiteInAndDateBetweenOrderByDateDesc(List<Website> sites, LocalDate d1, LocalDate d2, Pageable page);

    Page<Article> findBySiteNotInOrderByDateDesc(List<Website> sites,Pageable page);

    @Query(value="SELECT news_name FROM news  JOIN disliked ON disliked.n_id = news.news_id WHERE disliked.u_id=:id" , nativeQuery = true)
    List<String> extractNewsNamebyUserid(@Param("id") int id);


}