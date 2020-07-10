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

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    public String getSiteName(String siteName){
        return Optional.ofNullable(siteName).orElse("");
    }

    // http://localhost:8080/disable_news/1

    @GetMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String showAllSites(Model model, @PathVariable int id,String site_name) {

        List<News> allSites = disableNewsService.getAllSites();

        List<News> searchResultBySiteName = disableNewsService.findBySiteName(getSiteName(site_name));

        List<String> buttons = disableNewsService.getButtonsStatus(id,!getSiteName(site_name).isEmpty() ? searchResultBySiteName : allSites);

        model.addAttribute("allSites", !getSiteName(site_name).isEmpty() ? searchResultBySiteName : allSites);

        int columnCount=searchResultBySiteName.isEmpty() ? 5:1;

        log.info(fmt("User Id -> %d",id));
        model.addAttribute("user_id",id);

        model.addAttribute("colCount",columnCount);

        model.addAttribute("buttons",buttons);

        return "disable-news";

    }

    @PostMapping(value={"/disable_news/{id}","/disable_news/{id}/{news_id}"})
    public String disableNews(Model model,@RequestParam(required =false) String  operation
            ,@PathVariable int id, @PathVariable(required = false) Optional<Integer> news_id,String site_name){

        List<News> allSites = disableNewsService.getAllSites();

        log.info(fmt("Operation disable/enable -> %s",operation));

        if(String.valueOf(operation).equals("Disable")){
            log.info("adding dislike");
            log.info(fmt("news id that clicked is %d ", news_id.get()));
            disableNewsService.addDislike(id, news_id.get());
        }
        if(String.valueOf(operation).equals("Enable")){
            log.info("deleting dislike");
            log.info(fmt("news id that clicked is %d ", news_id.get()));
            disableNewsService.deleteDislike(id, news_id.get());
        }

        List<String> buttons = disableNewsService.getButtonsStatus(id,allSites);

        model.addAttribute("allSites", allSites);

        log.info(fmt("User Id -> %d",id));
        model.addAttribute("user_id",id);

        model.addAttribute("buttons",buttons);

        model.addAttribute("colCount",5);

        return "disable-news";
    }





}