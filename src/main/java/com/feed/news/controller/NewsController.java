package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.Article;
import com.feed.news.entity.News;
import com.feed.news.repository.ArticleRepo;
import com.feed.news.service.ArticleService;
import com.feed.news.service.DisableNewsService;
import com.feed.news.service.NewsFeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class NewsController {
    private final  NewsFeedService feedService;
    private final ArticleService articleService;
    private final DisableNewsService disableNewsService;

    public NewsController(NewsFeedService feedService, ArticleService articleService, DisableNewsService disableNewsService) {
        this.feedService = feedService;
        this.articleService = articleService;
        this.disableNewsService=disableNewsService;
    }


    // http://localhost:8080/news_feed/1
    @GetMapping("/news_feed/{id}")
    public String showDesignForm(Model model, @PathVariable int id, String keyword) {

        Stream<JsoupParser> newsParsers = feedService.getNewsParsers(id);

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        articleService.saveAll(articles);

        model.addAttribute("user_id",id);

        model.addAttribute("articles", keyword!=null ? articleService.findArticleByKeyword(keyword):articles);

        return "main-page";

    }

    // http://localhost:8080/disable_news/1

    @GetMapping("/disable_news/{id}")
    public String showAllSites(Model model, @PathVariable int id) {

        List<News> allSites = disableNewsService.getAllSites();

        model.addAttribute("allSites", allSites);

        return "disable-news";

    }




}