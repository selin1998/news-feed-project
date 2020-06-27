package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class InsiderParser implements JsoupParser{

    List<Article> articles = new ArrayList();
    @SneakyThrows
    @Override
    public List<Article> getArticles() {

        Document document = Jsoup.connect("https://www.insider.com/news").get();
        Elements elements = document.getElementsByClass("featured-post");
        for (Element element : elements) {
            String header = element.select(".tout-title-link").text();
            String content = element.select(".tout-copy").text();
            String link = "https://www.insider.com/".concat(element.select(".tout-title-link").attr("href"));
            String image = element.select(".lazy-image").first().attr("data-srcs").split("\":\\{")[0].substring(2);
            String date = element.select(".tout-timestamp").text();
            articles.add(new Article(header, content, link, image, date, Website.Insider));

        }
        return articles;
    }

}
