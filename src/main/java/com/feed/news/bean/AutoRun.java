package com.feed.news.bean;

import com.feed.news.entity.News;
import com.feed.news.entity.User;
import com.feed.news.repository.NewsFeedRepo;
import com.feed.news.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class AutoRun {


    private final UserRepo userRepo;
    private final NewsFeedRepo newsRepo;

    public AutoRun(UserRepo userRepo, NewsFeedRepo newsRepo) {

        this.userRepo = userRepo;

        this.newsRepo = newsRepo;
    }

//    @Bean
//    @Order(1)
    public CommandLineRunner add_news(){
        return args->{
            newsRepo.saveAll(Arrays.asList(
                    new News("TechCrunch","https://techcrunch.com/"),
                    new News("UberGizmo","https://www.ubergizmo.com/"),
                    new News("DroidLife","https://www.droid-life.com/"),
                    new News("TechStartups","https://techstartups.com/"),
                    new News("LenovaNews","https://news.lenovo.com/pressroom/press-releases/"),
                    new News("Insider","https://www.insider.com/news"),
                    new News("HTCNews","https://www.pocket-lint.com/htc"),
                    new News("DigitIn","https://www.digit.in/news/"),
                    new News("Policy","https://www.policygenius.com/blog/"),
            new News("Habr","https://habr.com/en/flows/develop/")
            ));

        };
    }


//    @Bean
//    @Order(2)
    public CommandLineRunner populate_user_with_news(){

            return args->{
                Set<News> newsSet=new HashSet<>(Arrays.asList(new News("Habr","https://habr.com/en/"),new News("Policy","https://www.policygenius.com/blog/")));
                User user1=new User("sherlock holmes","sholmes@gmail.com","watson",newsSet);
                userRepo.save(user1);


            };
        }




}
