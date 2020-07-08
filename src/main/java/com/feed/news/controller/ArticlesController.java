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
        return (s == null)? 0:Integer.parseInt(s);
    }



    @GetMapping("/news_feed/{id}")
    public String showDesignForm(Model model, @PathVariable int id, @RequestParam(required = false) String keyword, @RequestParam(required = false) String page, @RequestParam(required = false) String size) {

        Stream<JsoupParser> newsParsers = articleService.getNewsParsers(id);

        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());

        articleService.saveAll(articles);

        log.info(keyword);

        model.addAttribute("user_id",id);

        PageRequest pageable = PageRequest.of(valueOf(page),10);

        model.addAttribute("articles", keyword!=null ? articleService.findArticleByKeyword(keyword,id,pageable):articleService.getAllEnabled(id,pageable));

        model.addAttribute("keyword",keyword);

        return "main-page";

    }


    @PostMapping("/news_feed/{id}")
    public String sortByDate(Model model,@PathVariable int id,String news_start,@RequestParam(required = false) String keyword,String news_finish,@RequestParam(required = false) String page, @RequestParam(required = false) String size ){
        log.info(news_start);
        log.info(news_finish);
        PageRequest pageable = PageRequest.of(valueOf(page),10);
        model.addAttribute("keyword",keyword);
        model.addAttribute("user_id",id);
        model.addAttribute("articles", articleService.findArticleByDate(news_start,news_finish,id,pageable));
        return "main-page";
    }

}
