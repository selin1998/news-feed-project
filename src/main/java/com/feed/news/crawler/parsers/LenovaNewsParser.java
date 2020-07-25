package com.feed.news.crawler.parsers;

import com.feed.news.entity.db.Article;
import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LenovaNewsParser  implements JsoupParser {

    List<Article> articles = new ArrayList<>();



    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://news.lenovo.com/pressroom/press-releases/",this.getClass().getName());
        try{
            Elements elements = doc.getElementsByClass("card card-wide card-release");
            elements.stream().parallel().forEach(element->{
                String header = element.select(".card-title").text();
                String content = element.select(".card-text").text();
                String link = element.select(".card-title").first().select("a").attr("href");
                String image = element.select(".card-image").first().select("img").attr("src");
                LocalDate date = convertStringToDate(element.select(".card-date").text(), dateTimeForm.SIMPLE_MONTH_FORMAT);

                articles.add(new Article(header, content, link, image, date, Website.LenovaNews));

            });
        } catch (NullPointerException e) {
        }

        return articles;
    }
}

