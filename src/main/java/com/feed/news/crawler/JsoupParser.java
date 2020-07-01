package com.feed.news.crawler;

import com.feed.news.entity.Article;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public interface JsoupParser {


    List<Article> getArticles();


    default LocalDate convertStringToDate(String s, DateTimeFormatter formatter){
        try{
            return LocalDate.parse(s,formatter);
        }catch (DateTimeParseException ex){
            return LocalDate.of(2020,01,01);
        }

    }
}
