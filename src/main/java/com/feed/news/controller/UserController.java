package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.db.Article;
import com.feed.news.entity.db.XUser;
import com.feed.news.repository.ArticleRepo;
import com.feed.news.service.NewsFeedService;
import com.feed.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.userdetails.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

        private final NewsFeedService feedService;
        private final ArticleRepo articleRepo;
        private final UserService userService;

        public UserController(NewsFeedService feedService, ArticleRepo articleRepo, UserService userService) {
            this.feedService = feedService;
            this.articleRepo = articleRepo;
            this.userService = userService;
        }


    @ModelAttribute("registrationForm")
    public XUser registrationForm() {
        return new XUser();
    }

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
    public ModelAndView createNewUser(@ModelAttribute("registrationForm")  @Valid XUser user, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<XUser> userExists = userService.findUserByEmail(user.getEmail());
        if (user.getFull_name().isEmpty() || user.getEmail().isEmpty()) {
            bindingResult
                    .rejectValue("full_name", "error.user",
                            "Each field is mandatory");
        }

        if (userExists.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (!user.getPassword().equals(user.getConfirm_password())){
           bindingResult
                   .rejectValue("password", "error.user",
                           "The password fields must match");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }
        else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new XUser());

        }
        modelAndView.setViewName("registration");
        return  modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView showDesignForm(Model model) {

        ModelAndView modelAndView= new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        int id=userService.findUserByEmail(user.getUsername()).get().getUser_id();

        System.out.println(id);
        Stream<JsoupParser> newsParsers = feedService.getNewsParsers(id);
        List<Article> articles = newsParsers.flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());
        articleRepo.saveAll(articles);
        model.addAttribute("articles", articles);
        modelAndView.setViewName("news");
        return modelAndView;

    }
}
