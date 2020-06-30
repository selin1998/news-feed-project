//package com.feed.news.crawler.parsers;
//
//import com.feed.news.entity.Article;
//import com.feed.news.crawler.JsoupParser;
//import com.feed.news.crawler.Website;
//import lombok.SneakyThrows;
//import lombok.extern.log4j.Log4j2;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Log4j2
//public class MashableParser  implements JsoupParser {
//    static List<Article> articles = new ArrayList();
//
//    @SneakyThrows
//    @Override
//    public List<Article> getArticles() {
//        Document document = Jsoup.connect("https://mashable.com/category/startups/").get();
//        Elements elements = document.getElementsByTag("article");
//        for (Element element : elements) {
//            String header = element.select(".article-title").text();  //class->a->text
//            String content = element.select(".article-excerpt").text();
//            String link =element.select(".article-title").first()
//                    .getElementsByTag("a").first().attr("href");
//            String imageLink = element.getElementsByTag("img").attr("srcset");  //src
////            String date = element.getElementsByTag("time").first().attr("datetime").split("//+")[0];
//            String date = convertStringToDate(element.getElementsByTag("time").first().text(),);  //article-date->text()
//
//            log.info("mashable link:->"+link);
//            log.info("mashable imageLink:->"+imageLink);
//            log.info("mashable date:->"+date);
//
//
//            articles.add(new Article(header, content, link, imageLink, date, Website.Mashable));
//        }
//
//        return articles;
//    }
//}
//
