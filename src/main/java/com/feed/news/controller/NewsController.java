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

    private final DisableNewsService disableNewsService;

    public NewsController( DisableNewsService disableNewsService) {

        this.disableNewsService=disableNewsService;
    }




    // http://localhost:8080/disable_news/1

    @GetMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String showAllSites(Model model, @PathVariable int id) {

        List<News> allSites = disableNewsService.getAllSites();

        model.addAttribute("allSites", allSites);

        model.addAttribute("user_id",id);

        log.info(id);

        List<String> buttons = disableNewsService.getButtonsStatus(id,allSites);

        model.addAttribute("buttons",buttons);


        return "disable-news";

    }

    @PostMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String disableNews(Model model,@RequestParam(required =false) String  operation,@PathVariable int id, @PathVariable(required = false) Optional<Integer> news_id){

        List<News> allSites = disableNewsService.getAllSites();

        model.addAttribute("allSites", allSites);

        model.addAttribute("user_id",id);

        log.info(operation);

        if(String.valueOf(operation).equals("Disable")){
            log.info("adding dislike");
            log.info("news id that clicked is "+ news_id.get());
            disableNewsService.addDislike(id, news_id.get());
        }
        if(String.valueOf(operation).equals("Enable")){
            log.info("deleting dislike");
            log.info("news id that clicked is "+ news_id.get());
            disableNewsService.deleteDislike(id, news_id.get());
        }


        List<String> buttons = disableNewsService.getButtonsStatus(id,allSites);

        model.addAttribute("buttons",buttons);

        return "disable-news";
    }





}