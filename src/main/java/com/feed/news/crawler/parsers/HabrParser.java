package com.feed.news.crawler.parsers;

import com.feed.news.crawler.Article;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HabrParser implements JsoupParser {

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://habr.com/en/flows/develop/").get();
        Elements elements = document.getElementsByTag("article");
        for (Element element : elements) {
            String header = element.select(".post__title_link").text();
            String content = header;
            String link = element.select(".post__title_link").attr("href");
            String image = element.getElementsByTag("img").attr("src");
            String date = element.select(".post__time").text();

            articles.add(new Article(header, content, link, image, date, Website.Habr));
        }

        return articles;
    }

}
