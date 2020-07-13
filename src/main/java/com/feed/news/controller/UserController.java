package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.db.Article;
import com.feed.news.entity.db.XUser;
import com.feed.news.entity.XUserDetails;
import com.feed.news.repository.ArticleRepo;
import com.feed.news.service.NewsFeedService;
import com.feed.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

        private final NewsFeedService feedService;
        private final ArticleRepo articleRepo;

        public UserController(NewsFeedService feedService, ArticleRepo articleRepo) {
            this.feedService = feedService;
            this.articleRepo = articleRepo;
        }
    @Autowired
    private UserService userService;


    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        XUser user = new XUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid XUser user, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<XUser> userExists = userService.findUserByEmail(user.getEmail());
        if (userExists.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (!user.getPassword().equals(user.getConfirm_password())){
            model.addAttribute("passwordError", "Miss match");
            modelAndView.setViewName("registration");
            return  modelAndView;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new XUser());
            modelAndView.setViewName("registration");
            return  modelAndView;

        }
        return modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView showDesignForm(Model model) {
        ModelAndView modelAndView= new ModelAndView();
        XUserDetails xd = (XUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Stream<JsoupParser> newsParsers = feedService.getNewsParsers(xd.getId());
        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());
        articleRepo.saveAll(articles);
        model.addAttribute("articles", articles);
        modelAndView.setViewName("news");
        return modelAndView;

    }
}
