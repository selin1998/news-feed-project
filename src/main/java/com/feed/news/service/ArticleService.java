package com.feed.news.service;

import com.feed.news.crawler.JsoupParser;
import com.google.common.base.Enums;
import com.feed.news.crawler.Website;
import com.feed.news.entity.Article;
import com.feed.news.repository.ArticleRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo repo) {

        this.articleRepo = repo;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }


    public List<Website> newsDisabled(int id) {
        List<Website> disabled = articleRepo.extractNewsNamebyUserid(id).stream().map(a -> Website.valueOf(a)).collect(Collectors.toList());
        log.info(fmt("sites disabled %s ->" , disabled.toString()));
        return disabled;
    }


    public Page<Article> findArticleByKeyword(String keyword, int user_id, Pageable pageable) {

        return articleRepo.findAllByHeaderIgnoreCaseContainingAndSiteNotInOrContentIgnoreCaseContainingAndSiteNotInOrSiteEqualsAndSiteNotInOrderByDateDesc(
                keyword, newsDisabled(user_id), keyword, newsDisabled(user_id), Enums.getIfPresent(Website.class, keyword).orNull(), newsDisabled(user_id),pageable);

    }

    public Page<Article> getAllEnabled(int user_id,Pageable pageable) {
        return articleRepo.findBySiteNotInOrderByDateDesc(newsDisabled(user_id),pageable);
    }

    public List<Article> saveAll(List<Article> articles) {
        return articleRepo.saveAll(articles);
    }

    public Page<Article> findArticleByDate(String start_date, String finish_date, int user_id, Pageable pageable) {
        LocalDate date1 = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate date2 = LocalDate.parse(finish_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return articleRepo.findBySiteNotInAndDateBetweenOrderByDateDesc(newsDisabled(user_id), date1, date2,pageable);
    }

    public Stream<JsoupParser> getNewsParsers(int id) {

        Stream<Website> newsThatUserPrefers = EnumSet.allOf(Website.class).stream().filter(site -> !newsDisabled(id).contains(site));

        Stream<JsoupParser> newsParsers = newsThatUserPrefers.map(site -> site.getParser());

        return newsParsers;
    }

}
