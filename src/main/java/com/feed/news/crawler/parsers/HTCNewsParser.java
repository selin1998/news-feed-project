package com.feed.news.crawler.parsers;

import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import com.feed.news.entity.db.Article;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HTCNewsParser  implements JsoupParser {

    List<Article> articles = new ArrayList<>();



    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://www.pocket-lint.com/htc",this.getClass().getName());
        try{
            Elements elements = doc.getElementsByClass("article");
            elements.stream().parallel().forEach(element-> {
                        String header = element.select(".article-info-title").text();
                        String content = element.select(".article-info-description").text();
                        String link = element.getElementsByTag("a").first().attr("href");
                        String image = element.select(".article-thumbnail").first().select("img").first().attr("src");
                        LocalDate date = convertStringToDate(element.getElementsByTag("time").text(), dateTimeForm.SIMPLE_MONTH_FORMAT);

                        articles.add(new Article(header, content, link, image, date, Website.HTCNews));
                    });

        } catch (NullPointerException e) {

        }

        return articles;
    }
}
