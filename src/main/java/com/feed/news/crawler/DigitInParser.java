package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DigitInParser extends News implements JsoupParser {
    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.digit.in/news/").get();
        Elements elements = document.getElementsByClass("Thumb-new");
        for (Element element : elements) {
            String header = element.select(".product-desc").text();
            String content = element.select(".product-desc").text();
            String link =element.getElementsByTag("a").first().attr("href");
            String imageLink = element.getElementsByTag("img").first().attr("data-src");
            String date = element.getElementsByTag("span").first().text();

            articles.add(new Article(header, content, link, imageLink, date, Website.DigitIn));
        }

        return articles;
    }
}



