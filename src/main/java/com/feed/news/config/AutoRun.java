package com.feed.news.config;

import com.feed.news.entity.db.News;
import com.feed.news.entity.db.XUser;
import com.feed.news.repository.NewsFeedRepo;
import com.feed.news.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class AutoRun {


    private final UserRepository userRepo;
    private final NewsFeedRepo newsRepo;

    public AutoRun(UserRepository userRepo, NewsFeedRepo newsRepo) {

        this.userRepo = userRepo;

        this.newsRepo = newsRepo;
    }

//    @Bean
//    @Order(1)
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


//    @Bean
//    @Order(2)
    public CommandLineRunner populate_user_with_news(){

            return args->{
                Set<News> newsSet=new HashSet<>(Arrays.asList(new News("Habr","https://habr.com/en/"),new News("Policy","https://www.policygenius.com/blog/")));
                XUser user1=new XUser("sherlock holmes","sholmes@gmail.com","watson",newsSet);
                userRepo.save(user1);


            };
        }




}
