package com.feed.news.controller;

import com.feed.news.entity.Mail;
import com.feed.news.entity.PasswordForgotDto;
import com.feed.news.entity.db.PasswordResetToken;
import com.feed.news.entity.db.XUser;
import com.feed.news.repository.PasswordResetTokenRepository;
import com.feed.news.service.EmailService;
import com.feed.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }

    @GetMapping
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()) {
            return "forgot-password";
        }

        Optional<XUser> user = userService.findUserByEmail(form.getEmail());
        if (!user.isPresent()) {
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user.get());
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("no-reply@ibatech.com");
        mail.setTo(user.get().getEmail());
        mail.setSubject("Password reset request");
        mail.setContent("Dear " + user.get().getFull_name() + "\n\nTo complete the password reset process, please click here: "
                + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/reset-password?token=" + token.getToken());
        emailService.sendEmail(mail);
        return "redirect:/forgot-password?success";

    }

}