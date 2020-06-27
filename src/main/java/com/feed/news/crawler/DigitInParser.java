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
//            System.out.println(header);
//            System.out.println(content);
//            System.out.println(link);
//            System.out.println(imageLink);
//            System.out.println(date);

        return articles;
    }
}


//1
//for (Element element : elements) {
//        String header = element.select(".product-desc").text();  //<a><div class="product-desc">
//        String content = header;
//        String link = element.getElementsByTag("a").first().attr("href");
//        String imageLink = element.getElementsByTag("img").first().attr("src");
//        String date = element.getElementsByTag("span").first().text();
//
//        articles.add(new Article(header, content, link, imageLink, date, Website.DigitIn));
//        }


//2
//Document document = Jsoup.connect("https://www.digit.in/news/").get();
//    Elements elements = document.getElementsByClass("Thumb-new");
//        for (Element element : elements) {
//                String header = element.select(".product-desc").text();  //<a><div class="product-desc">
//                String content = element.select(".product-desc").text();
//                String link = element.select("a").first().attr("href");
//                String imageLink = element.select(".Search-product").first()
//                .select("img").first().attr("src");
//                String date = element.select("span").first().text();
//
//                articles.add(new Article(header, content, link, imageLink, date, Website.DigitIn));
//                }



