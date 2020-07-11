package com.feed.news.controller;

import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.Article;
import com.feed.news.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Component
public class Scheduler {

    private final ArticleService articleService;

    public Scheduler(ArticleService articleService) {

        this.articleService = articleService;
    }

    @Scheduled(fixedRate =1800000, initialDelay = 10000) // every 30 minutes parse, try fixedRate=60000 for one minute
    public void parseArticles(){

        log.info("fetching articles-> time:"+new SimpleDateFormat("HH:mm:ss").format(new Date()));

        Stream<JsoupParser> newsParsers = articleService.getNewsParsers();

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        articleService.saveAll(articles);
    }

}
