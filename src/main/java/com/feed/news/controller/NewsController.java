package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.Article;
import com.feed.news.service.ArticleService;
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

    public NewsController(NewsFeedService feedService, ArticleService articleService) {
        this.feedService = feedService;

        this.articleService = articleService;
    }


    // http://localhost:8080/news_feed/1
    @GetMapping("/news_feed/{id}")
    public String showDesignForm(Model model, @PathVariable int id, String keyword) {

        Stream<JsoupParser> newsParsers = feedService.getNewsParsers(id);

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        articleService.saveAll(articles);

        model.addAttribute("user_id",String.valueOf(id));
        model.addAttribute("articles", keyword!=null ? articleService.findArticleByKeyword(keyword):articles);

        return "main-page";

    }


}