package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PolicyParser extends News implements JsoupParser {

    static List<Article> articles = new ArrayList();
    @SneakyThrows
    @Override
    public List<Article> getArticles()  {
        Document document = Jsoup.connect("https://www.policygenius.com/blog/").get();
        Elements elements = document.getElementsByClass("teaser");

        for (Element element : elements) {

            String header = element.select(".teaser__title").text();
            String content = header;
            String link = String.format("%s%s","https://www.policygenius.com",element.select(".teaser__title").select("a").attr("href"));
            String image = element.select(".picture__image").attr("data-src");
            String date = element.getElementsByTag("time").text();

            articles.add(new Article(header,content,link,image,date));

        }
        return articles;
    }

}
