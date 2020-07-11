package com.feed.news.service;

import com.feed.news.crawler.Website;
import com.feed.news.entity.News;
import com.feed.news.repository.NewsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisableNewsService {
    private final NewsRepo feedRepo;

    public DisableNewsService(NewsRepo feedRepo) {

        this.feedRepo = feedRepo;
    }

    public List<News> getAllSites(){
        return feedRepo.findAll();
    }

    public List<News> findBySiteName(String site){
        return feedRepo.findByNews_name(site);
    }

    public void addDislike(int id, int news_id) {
        feedRepo.addDislike(id,news_id);
    }

    public void deleteDislike(int id, int news_id){feedRepo.deleteDislike(id,news_id);}

    public  List<Integer> getDislikesByUserId(int user_id){return feedRepo.getAllDislikes(user_id);}

    public List<String> getButtonsStatus(int user_id, List<News> allSites){

        return allSites.stream().map(site ->
                getDislikesByUserId(user_id).contains(site.getNews_id()) ? "Off-" : "On+")
                .collect(Collectors.toList());
    }
}
