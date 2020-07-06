package com.feed.news.crawler;

import com.feed.news.entity.db.Article;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public interface JsoupParser {

    public List<Article> getArticles();

    default LocalDate convertStringToDate(String s, DateTimeFormatter formatter){
        try{
            return LocalDate.parse(s,formatter);
        }catch (DateTimeParseException ex){
            return LocalDate.of(2020,01,01);
        }

    }
}
