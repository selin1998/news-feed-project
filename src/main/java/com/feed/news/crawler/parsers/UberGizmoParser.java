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

public class UberGizmoParser  implements JsoupParser {

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.ubergizmo.com/").get();
        Elements elements = document.getElementsByClass("article_card");
        for (Element element : elements) {
            String header = element.select(".article_card_title").text();
            String content = element.select(".article_card_excerpt").text();
            String link = element.select(".article_card_title").first().select("a").first().attr("href");
            String image = element.select(".article_card_divimg").first().attr("data-bg");
            LocalDate date = convertStringToDate(element.select(".byline").text().split( ", on")[1], DateTimeFormats.PDT_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.UberGizmo));



        }
        return articles;
    }
}

