package com.feed.news.controller;


import com.feed.news.entity.News;
import com.feed.news.service.DisableNewsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@Controller
@RequestMapping("/")
public class NewsController {

    private final DisableNewsService disableNewsService;

    public NewsController( DisableNewsService disableNewsService) {

        this.disableNewsService=disableNewsService;
    }

    public String getSiteName(String siteName){
        return Optional.ofNullable(siteName).orElse("");
    }

    // http://localhost:8080/disable_news/1

    @GetMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String showAllSites(Model model, @PathVariable int id,String site_name) {

        List<News> allSites = disableNewsService.getAllSites();

        log.info(id);

        List<String> buttons = disableNewsService.getButtonsStatus(id,allSites);

        model.addAttribute("allSites", !getSiteName(site_name).isEmpty() ? disableNewsService.findBySiteName(site_name) : allSites);

        int columnCount=disableNewsService.findBySiteName(getSiteName(site_name)).isEmpty() ? 5:1;

        model.addAttribute("colCount",columnCount);

        model.addAttribute("user_id",id);

        model.addAttribute("buttons",buttons);

        return "disable-news";

    }

    @PostMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String disableNews(Model model,@RequestParam(required =false) String  operation
            ,@PathVariable int id, @PathVariable(required = false) Optional<Integer> news_id){

        List<News> allSites = disableNewsService.getAllSites();

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

        model.addAttribute("allSites", allSites);

        model.addAttribute("user_id",id);

        model.addAttribute("buttons",buttons);

        model.addAttribute("colCount",5);

        return "disable-news";
    }





}