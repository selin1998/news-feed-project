package com.feed.news.crawler.parsers;

import com.feed.news.crawler.Article;
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

public class LenovaParser implements JsoupParser {

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://news.lenovo.com/pressroom/press-releases/").get();
        Elements elements = document.getElementsByClass("col-12_xs-12");
        for (Element element : elements) {
            String header = element.select(".card-title").text();
            String content = element.select(".card-text").text();
            String link = element.select(".card-title").first().select("a").attr("href");
            String image = element.select(".card-image").first().select("img").attr("src");
            LocalDate date = convertStringToDate(element.select(".card-date").text(), DateTimeFormats.SIMPLE_MONTH_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.Lenova));

                    }
        return articles;
    }
}

