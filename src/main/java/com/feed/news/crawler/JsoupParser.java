package com.feed.news.crawler;

import com.feed.news.entity.db.Article;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;



public interface JsoupParser {



    DateTimeFormats dateTimeForm=new DateTimeFormats();

    List<Article> getArticles();

     default LocalDate convertStringToDate(String s, DateTimeFormatter formatter){
        try{
            return LocalDate.parse(s,formatter);
        }catch (DateTimeParseException ex){
            return LocalDate.now();
        }

    }

    default Document connection(String url, String className){
        Document doc =null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();
        } catch (NullPointerException e) {
            System.out.printf("NullPointerException in %s\n",className);
        } catch (HttpStatusException e) {
            System.out.println("HttpStatus Exception");
        } catch (IOException e) {
            System.out.printf("IO Exception %s\n", className);
        }
        return doc;
    }
}
