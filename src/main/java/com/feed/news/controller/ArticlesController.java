package com.feed.news.controller;

import com.feed.news.entity.db.Article;
import com.feed.news.service.ArticleService;
import com.feed.news.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/")
public class ArticlesController {

    private final ArticleService articleService;

    private final UserService userService;

    public ArticlesController(ArticleService articleService, UserService userService) {

        this.articleService = articleService;
        this.userService = userService;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    public int valueOf(String s, int val) {
        return Optional.ofNullable(s).map(Integer::parseInt).orElse(val);
    }

    public int pageNum(String num, int page){
        return (valueOf(num,0)>0)?valueOf(num,0)-1:page;
    }

    public String getKeyword(String s){
        return Optional.ofNullable(s).map(String::valueOf).orElse("");
    }

    public boolean existsDate(String s1, String s2){
        return getKeyword(s1)!="" && getKeyword(s2)!="";
    }

    // http://localhost:5000/news_feed

    @GetMapping("/news_feed")
    public String showDesignForm(Model model, @RequestParam(required = false)String news_start
            , @RequestParam(required = false)String news_finish, @RequestParam(required = false) String keyword
            , @RequestParam(required = false) String page, @RequestParam(required = false) String size, @RequestParam(required = false) String  num) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        int user_id=userService.findUserByEmail(user.getUsername()).get().getUser_id();

        String username = userService.findUserByEmail(user.getUsername()).get().getFull_name();

        log.info("user id: "+user_id);

        log.info("username"+username);

        PageRequest pageable = PageRequest.of(pageNum(num,valueOf(page,0)),valueOf(size,10));

        Page<Article> result= existsDate(news_start,news_finish) ? articleService.findArticleByDate(news_start,news_finish,user_id,pageable)
                : articleService.findArticleByKeyword(getKeyword(keyword),user_id,pageable);

        int totalPages = result.getTotalPages();
        int pageNumber = result.getNumber() + 1;

        if(totalPages>10){
            Paginator p=new Paginator();
            List<Integer> paginator = p.getPaginator(pageNumber, totalPages);
            model.addAttribute("custompager",paginator);
        }

        model.addAttribute("articles", result);

        log.info(fmt("Keyword for search ->  %s", keyword));
        model.addAttribute("keyword",keyword);

        log.info(fmt("Start date for search ->  %s",news_start));
        model.addAttribute("news_start",news_start);

        log.info(fmt("Finish date for search ->  %s",news_finish));
        model.addAttribute("news_finish",news_finish);

        log.info(fmt("User id -> %d",user_id));
        model.addAttribute("user_id",user_id);

        model.addAttribute("username",username);


        return "main-page";

    }


}
