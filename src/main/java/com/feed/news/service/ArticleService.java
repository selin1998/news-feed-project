package com.feed.news.service;
import com.google.common.base.Enums;
import com.feed.news.crawler.Website;
import com.feed.news.entity.Article;
import com.feed.news.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo repo) {

        this.articleRepo= repo;
    }

    public List<Article> findArticleByKeyword(String keyword){
        return articleRepo.findByHeaderIgnoreCaseContainingOrContentIgnoreCaseContainingOrSiteEquals( keyword,keyword, Enums.getIfPresent(Website.class, keyword).orNull());
    }
    public List<Article> saveAll(List<Article> articles){
        return articleRepo.saveAll(articles);
    }

    public List<Article>findArticleByDate(String start_date, String finish_date){
        LocalDate date1 = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate date2 = LocalDate.parse(finish_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return articleRepo.findByDateBetween(date1,date2);
    }

//    public List<Article> findArticleBySite(String site){
//       return articleRepo.findBySiteEquals( Website.valueOf(site));
//    }
}
