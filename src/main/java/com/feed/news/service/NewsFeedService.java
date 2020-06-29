package com.feed.news.service;


import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import com.feed.news.repository.NewsFeedRepo;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsFeedService {
    private final NewsFeedRepo feedRepo;

    public NewsFeedService(NewsFeedRepo feedRepo) {
        this.feedRepo = feedRepo;
    }

    public Stream<JsoupParser> getNewsParsers(int id){

        List<String> dismissedNewsNames = feedRepo.extractbyUserid(id);

        Stream<String> newsThatUserPrefers = EnumSet.allOf(Website.class).stream().map(Website::name).filter(name -> !dismissedNewsNames.contains(name));

        Stream<JsoupParser> newsParsers = newsThatUserPrefers.map(news -> Website.valueOf(news)).map(site -> site.getParser());

        return newsParsers;
    }
}
