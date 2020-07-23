package com.feed.news.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Controller
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String handle(HttpServletRequest request, HttpServletResponse response, Exception ex, Model model){

        model.addAttribute("exception",ex);
        model.addAttribute("path",request.getPathInfo());
        model.addAttribute("url",request.getRequestURL());
        model.addAttribute("status",response.getStatus());

        return "error-page";
    }


}
