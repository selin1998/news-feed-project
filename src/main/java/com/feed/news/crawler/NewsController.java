package com.feed.news.crawler;


import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.feed.news.crawler.PolicyParser.articles;

@Controller
public class NewsController {


    @GetMapping("/test")
    public String showDesignForm(Model model) {
        JsoupParser [] parsers=new JsoupParser[]{new HabrParser(),new InsiderParser(),new TechCrunchParser(),new PolicyParser()
                ,new DigitInParser() ,new BBCParser() ,new TheNextWebParser(),new TechStartupsParser() };
//        JsoupParser techCrunchParser = new TechCrunchParser();
//        JsoupParser  policyParser= new PolicyParser();
//        JsoupParser habrParser = new HabrParser();
        List<Article> articles = Arrays.stream(parsers).flatMap(p -> p.getArticles().stream()).collect(Collectors.toList());
        model.addAttribute("articles", articles);
        return "main-page";
    }


}