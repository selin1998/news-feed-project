package com.feed.news.service;

import com.feed.news.crawler.Website;
import com.feed.news.entity.News;
import com.feed.news.repository.NewsFeedRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisableNewsService {
    private final NewsFeedRepo feedRepo;

    public DisableNewsService(NewsFeedRepo feedRepo) {
        this.feedRepo = feedRepo;
    }

    public List<News> getAllSites(){
        return feedRepo.findAll();
    }


    public void addDislike(int id, int news_id) {
        feedRepo.addDislike(id,news_id);
    }
}
