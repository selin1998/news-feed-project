package com.feed.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.feed.news.controller"})
@ComponentScan({"com.feed.news.controller","com.feed.news.service","com.feed.news.repository","com.feed.news.bean"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
