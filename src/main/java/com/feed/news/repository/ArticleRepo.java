package com.feed.news.repository;

import com.feed.news.crawler.Website;
import com.feed.news.entity.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepo extends JpaRepository<Article,String> {

    // (a^b^c)&d=(a&d)^(b&d)^(c&d) -> (header^content^siteEquals)&(siteNotIn) : for search bar
    List<Article> findAllByHeaderIgnoreCaseContainingAndSiteNotInOrContentIgnoreCaseContainingAndSiteNotInOrSiteEqualsAndSiteNotInOrderByDateDesc(String keyword1,List<Website> sites1, String keyword2,List<Website> sites2, Website keyword3 , List<Website> sites3);

    List<Article> findBySiteNotInAndDateBetweenOrderByDateDesc(List<Website> sites,LocalDate d1, LocalDate d2);

    List<Article> findBySiteNotInOrderByDateDesc(List<Website> sites);

    @Query(value="SELECT news_name FROM news  JOIN disliked ON disliked.n_id = news.news_id WHERE disliked.u_id=:id" , nativeQuery = true)
    List<String> extractNewsNamebyUserid(@Param("id") int id);


}