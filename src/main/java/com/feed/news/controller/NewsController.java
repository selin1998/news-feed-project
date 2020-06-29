package com.feed.news.crawler;


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

    public NewsController(NewsFeedService feedService) {
        this.feedService = feedService;
    }


    // http://localhost:8080/news_feed/1
    @GetMapping("/news_feed/{id}")
    public String showDesignForm(Model model, @PathVariable int id) {

        Stream<JsoupParser> newsParsers = feedService.getNewsParsers(id);

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        model.addAttribute("articles", articles);

        return "main-page";

    }


}