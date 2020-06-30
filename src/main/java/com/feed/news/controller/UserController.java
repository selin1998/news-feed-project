package com.feed.news.controller;


import com.feed.news.entity.User;
import com.feed.news.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public @ResponseBody
//    ModelAndView handleSignupForm(@ModelAttribute User candidate, HttpServletResponse response) throws ServiceException {
//        Set<ConstraintViolation<User>> failures = validator
//                .validate(candidate);
//
////        if (!failures.isEmpty()) {
////            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//////            return ValidationHelper.validationMessages(failures);
////
////        } else {
////            return userService.saveUser(candidate);
////        }
////    }
//
}
