package com.feed.news.controller;


import com.feed.news.entity.News;
import com.feed.news.service.DisableNewsService;
import com.feed.news.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@Controller
@RequestMapping("/")
public class NewsController {

    private final DisableNewsService disableNewsService;

    private UserService userService;

    public NewsController( DisableNewsService disableNewsService, UserService userService) {

        this.disableNewsService=disableNewsService;
        this.userService=userService;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    public String getSiteName(String siteName){
        return Optional.ofNullable(siteName).orElse("");
    }

    // http://localhost:8080/disable_news/1

    @GetMapping(value={"/disable_news","/disable_news/{news_id}"})
    public String showAllSites(Model model, String site_name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        int user_id=userService.findUserByEmail(user.getUsername()).get().getUser_id();

        String username = userService.findUserByEmail(user.getUsername()).get().getFull_name();

        String imageAsBase64 = userService.findUserByEmail(user.getUsername()).get().getImageAsBase64();

        List<News> allSites = disableNewsService.getAllSites();

        List<News> searchResultBySiteName = disableNewsService.findBySiteName(getSiteName(site_name));

        List<String> buttons = disableNewsService.getButtonsStatus(user_id,!getSiteName(site_name).isEmpty() ? searchResultBySiteName : allSites);

        model.addAttribute("allSites", !getSiteName(site_name).isEmpty() ? searchResultBySiteName : allSites);

        int columnCount=searchResultBySiteName.isEmpty() ? 5:1;

        log.info(fmt("User Id -> %d",user_id));
        model.addAttribute("user_id",user_id);

        model.addAttribute("colCount",columnCount);

        model.addAttribute("buttons",buttons);

        model.addAttribute("username",username);

        model.addAttribute("imageAsBase64",imageAsBase64);

        return "disable-news";

    }

    @PostMapping(value={"/disable_news","/disable_news/{news_id}"})
    public String disableNews(Model model,@RequestParam(required =false) String  operation
            , @PathVariable(required = false) Optional<Integer> news_id,String site_name){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        int user_id=userService.findUserByEmail(user.getUsername()).get().getUser_id();

        String username = userService.findUserByEmail(user.getUsername()).get().getFull_name();

        String imageAsBase64 = userService.findUserByEmail(user.getUsername()).get().getImageAsBase64();

        List<News> allSites = disableNewsService.getAllSites();

        log.info(fmt("Operation disable/enable -> %s",operation));

        if(String.valueOf(operation).equals("On+")){
            log.info("adding dislike");
            log.info(fmt("news id that clicked is %d ", news_id.get()));
            disableNewsService.addDislike(user_id, news_id.get());
        }
        if(String.valueOf(operation).equals("Off-")){
            log.info("deleting dislike");
            log.info(fmt("news id that clicked is %d ", news_id.get()));
            disableNewsService.deleteDislike(user_id, news_id.get());
        }

        List<String> buttons = disableNewsService.getButtonsStatus(user_id,allSites);

        model.addAttribute("allSites", allSites);

        log.info(fmt("User Id -> %d",user_id));
        model.addAttribute("user_id",user_id);

        model.addAttribute("buttons",buttons);

        model.addAttribute("colCount",5);

        model.addAttribute("username",username);

        model.addAttribute("imageAsBase64",imageAsBase64);

        return "disable-news";
    }





}