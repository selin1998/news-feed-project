package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TheNextWebParser extends News implements JsoupParser {
    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://thenextweb.com/latest/").get();
        Elements elements = document.getElementsByTag("li");
        for (Element element : elements) {
            String header = element.select(".story-title").first()
                    .getElementsByTag("a").first().text();
            String content = element.select(".story-chunk").text();  //tag--><p>.text()
            String link =element.select(".story-title").first()
                    .getElementsByTag("a").first().attr("href");
//            String imageLink = element.select(".Search-product").first()
//                    .getElementsByTag("img").first().attr("src");
            String imageLink = element.select(".lazy story-image").first().attr("src");  //?
            String date = element.getElementsByTag("time").first().attr("data-full-date");  //datatime

            articles.add(new Article(header, content, link, imageLink, date, Website.TheNextWeb));
        }

        return articles;
    }
}
