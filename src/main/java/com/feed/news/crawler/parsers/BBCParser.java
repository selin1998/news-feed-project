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

public class BBCParser implements JsoupParser {

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.bbc.co.uk/news").get();
        Elements elements = document.getElementsByClass("nw-c-top-stories__secondary-item");  //gel-layout__item
        for (Element element : elements) {
            String header = element.select(".gs-c-promo-heading__title").text();
            String content = element.getElementsByTag("p").text();   //class--> .gs-c-promo-summary
            String link =element.getElementsByTag("a").first().attr("href");  //class-->gs-c-promo-heading || gs-c-section-link
            //  String link =element.getElementsByTag("a").first().attr("abs:href");
            String imageLink = element.getElementsByTag("img").first().attr("data-src");  //src-->no
//            String date = element.select("[datetime]").first().text();   //tag--> <time>  // result: 7 our ago =(
            String date = element.getElementsByTag("time").first().attr("datetime");   //tag--> <time>


            articles.add(new Article(header, content, link, imageLink, date, Website.BBC));
        }

        return articles;
    }
}
