package com.feed.news.controller;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.entity.db.Article;
import com.feed.news.entity.db.XUser;
import com.feed.news.repository.ArticleRepo;
import com.feed.news.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
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

@Log4j2
@Controller
public class UserController {

    private final ArticleRepo articleRepo;
    private final UserService userService;

    public UserController( ArticleRepo articleRepo, UserService userService) {

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
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("registrationForm")  @Valid XUser user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<XUser> userExists = userService.findUserByEmail(user.getEmail());

        if (user.getFull_name().isEmpty() || user.getEmail().isEmpty()) {
            bindingResult.rejectValue("full_name", "error.user", "Each field is mandatory");
        }
        if (userExists.isPresent()) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if (!user.getPassword().equals(user.getConfirm_password())){
           bindingResult.rejectValue("password", "error.user", "The password fields must match");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }
        else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
        }
        modelAndView.setViewName("registration");
        return  modelAndView;
    }


}
