package com.feed.news.controller;

import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.Article;
import com.feed.news.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Controller
@RequestMapping("/")
public class ArticlesController {


    private final ArticleService articleService;

    public ArticlesController(ArticleService articleService) {

        this.articleService = articleService;
    }

    // http://localhost:8080/news_feed/1


    public int valueOf(String s) {
        return Optional.ofNullable(s).map(Integer::parseInt).orElse(0);
    }

    public String getKeyword(String s){
        return Optional.ofNullable(s).map(String::valueOf).orElse("");
    }

    public boolean existsDate(String s1, String s2){
       return getKeyword(s1)!="" && getKeyword(s2)!="";
    }



    @GetMapping("/news_feed/{id}")
    public String showDesignForm(Model model, @PathVariable int id, @RequestParam(required = false)String news_start, @RequestParam(required = false)String news_finish, @RequestParam(required = false) String keyword, @RequestParam(required = false) String page, @RequestParam(required = false) String size) {

        Stream<JsoupParser> newsParsers = articleService.getNewsParsers(id);

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        articleService.saveAll(articles);

        log.info(keyword);
        log.info(news_start);
        log.info(news_finish);

        PageRequest pageable = PageRequest.of(valueOf(page),10);

        model.addAttribute("articles", existsDate(news_start,news_finish) ? articleService.findArticleByDate(news_start,news_finish,id,pageable) : articleService.findArticleByKeyword(getKeyword(keyword),id,pageable));

        model.addAttribute("keyword",keyword);

        model.addAttribute("news_start",news_start);

        model.addAttribute("news_finish",news_finish);

        model.addAttribute("user_id",id);

        return "main-page";

    }


}
