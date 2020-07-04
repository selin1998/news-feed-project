package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.Article;
import com.feed.news.entity.News;
import com.feed.news.service.ArticleService;
import com.feed.news.service.DisableNewsService;
import com.feed.news.service.NewsFeedService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
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


    @PostMapping("/news_feed/{id}")
    public String sortByDate(Model model,@PathVariable int id,String news_start, String news_finish ){
        log.info(news_start);
        log.info(news_finish);
        model.addAttribute("user_id",id);
        model.addAttribute("articles", articleService.findArticleByDate(news_start,news_finish));
        return "main-page";
    }

    // http://localhost:8080/disable_news/1

    @GetMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String showAllSites(Model model, @PathVariable int id, @PathVariable(required = false) Optional<Integer> news_id, String operation) {

        List<News> allSites = disableNewsService.getAllSites();

        model.addAttribute("allSites", allSites);

        model.addAttribute("user_id",id);

        log.info(id);
        log.info(operation);

        if(String.valueOf(operation).equals("enable")){
            log.info("adding dislike");
            log.info("news id that clicked is "+ news_id.get());
            disableNewsService.addDislike(id, news_id.get());
        }


        return "disable-news";

    }

//    @PostMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
//    public String disableNews(){
//
//    }





}