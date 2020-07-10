package com.feed.news.crawler;

import com.feed.news.entity.Article;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;



public interface JsoupParser {




    List<Article> getArticles() ;


    default LocalDate convertStringToDate(String s, DateTimeFormatter formatter){
        try{
            return LocalDate.parse(s,formatter);
        }catch (DateTimeParseException ex){
            return LocalDate.of(2020,01,01);
        }

    }
}
