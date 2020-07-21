package com.feed.news.controller;


import com.feed.news.entity.db.ConfirmationToken;
import com.feed.news.entity.db.XUser;
import com.feed.news.repository.ArticleRepo;
import com.feed.news.repository.ConfirmationTokenRepository;
import com.feed.news.repository.UserRepo;
import com.feed.news.service.EmailSenderService;
import com.feed.news.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@Controller
public class UserController {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepo userRepository;

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
    public ModelAndView createNewUser(@ModelAttribute("registrationForm")  @Valid XUser user, BindingResult bindingResult,  HttpServletRequest request) {

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
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("new.news.2020@gmail.com");
            mailMessage.setText("To confirm your account, please click here : " +
                    request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    +"/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            modelAndView.addObject("email", user.getEmail());


            modelAndView.addObject("successMessage", "User has been registered successfully");
        }
        modelAndView.setViewName("registration");
        return  modelAndView;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            XUser user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("login");
            modelAndView.addObject("confirmMessage", "Congratulations! Your account has been activated and email is verified! Please Login");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}
