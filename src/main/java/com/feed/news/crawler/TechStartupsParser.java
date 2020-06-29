//package com.feed.news.crawler;
//
//import lombok.SneakyThrows;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TechStartupsParser extends News implements JsoupParser {
//
//    static List<Article> articles = new ArrayList();
//
//    @SneakyThrows
//    @Override
//    public List<Article> getArticles() {
//        Document document = Jsoup.connect("https://techstartups.com/").get();
//        Elements elements = document.getElementsByClass("post");
//        for (Element element : elements) {
//            String header = element.select(".post_header_title").first().getElementsByTag("a").first().text();
//            String content = element.select(".post_header_title").first().getElementsByTag("p").first().text();
//            String link =element.select(".post_header_title").first()
//                    .getElementsByTag("a").first().attr("href");
//            //  String link =element.getElementsByTag("a").first().attr("abs:href");
//            String imageLink = element.getElementsByTag("img").first().attr("src");  //class->a->img
//            String date = element.select("post_info_date").first().getElementsByTag("a").first().text();
//
//
//            articles.add(new Article(header, content, link, imageLink, date, Website.BBC));
//        }
//
//        return articles;
//    }
//}
