package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MacWorldParser extends News implements JsoupParser{

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.androidcentral.com/").get();
        Elements elements = document.getElementsByClass("loop-panel");
        for (Element element : elements) {
            String header = element.select(".article-title").text();
            String content = element.select(".excerpt").text();
            String link = element.select(".loop-info").first().select("a").attr("href");
            String image = element.select(".loop-image b-lazy b-loaded").attr("data-src");
            String date = element.select(".aa_item_time").text();

            articles.add(new Article(header, content, link, image, date, Website.MacWorld));

                    }
        return articles;
    }
}

