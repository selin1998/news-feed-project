package com.feed.news.config;

import com.feed.news.entity.db.News;
import com.feed.news.repository.NewsFeedRepo;
import com.feed.news.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;


@Configuration
public class AutoRun {


    private final UserRepo userRepo;
    private final NewsFeedRepo newsRepo;

    public AutoRun(UserRepo userRepo, NewsFeedRepo newsRepo) {

        this.userRepo = userRepo;

        this.newsRepo = newsRepo;
    }

    @Bean
    @Order(1)
    public CommandLineRunner add_news(){
        return args->{
            newsRepo.saveAll(Arrays.asList(
                    new News("TechCrunch","https://techcrunch.com/"),
                    new News("Policy","https://www.policygenius.com/blog/"),
                    new News("Habr","https://habr.com/en/flows/develop/"),
                    new News("Insider","https://www.insider.com/news"),
                    new News("DigitIn","https://www.digit.in/news/"),
                    new News("BBC","https://www.bbc.co.uk/news"),
                    new News("TheNextWeb","https://thenextweb.com/latest/"),
                    new News("TechStartups","https://techstartups.com/"),
                    new News("UberGizmo","https://techstartups.com/"),
                    new News("Mashable","https://techstartups.com/"),
                    new News("DroidLife","https://techstartups.com/")
            ));

        };
    }






}
