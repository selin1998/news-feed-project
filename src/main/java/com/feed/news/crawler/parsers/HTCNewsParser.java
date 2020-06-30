package com.feed.news.crawler.parsers;

import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import com.feed.news.entity.Article;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HTCNewsParser implements JsoupParser {

    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.pocket-lint.com/htc").get();
        Elements elements = document.getElementsByClass("article");
        for (Element element : elements) {
            String header = element.select(".article-info-title").text();
            String content = element.select(".article-info-description").text();
            String link = element.getElementsByTag("a").first().attr("href");
            String image = element.select(".article-thumbnail").first().select("img").first().attr("src");
            LocalDate date = convertStringToDate(element.getElementsByTag("time").text(), DateTimeFormats.SIMPLE_MONTH_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.HTCNews));



        }
        return articles;
    }
}
