package com.feed.news.crawler.parsers;

import com.feed.news.entity.db.Article;
import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UberGizmoParser  implements JsoupParser {

    List<Article> articles = new ArrayList<>();
    Document doc;


    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://www.ubergizmo.com/");
        Elements elements = doc.getElementsByClass("article_card");
        for (Element element : elements) {
            String header = element.select(".article_card_title").text();
            String content = element.select(".article_card_excerpt").text();
            String link = element.select(".article_card_title").first().select("a").first().attr("href");
            String image = element.select(".article_card_divimg").first().attr("data-bg");
            LocalDate date = convertStringToDate(element.select(".byline").text().split(", on")[1], dateTimeForm.PDT_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.UberGizmo));

        }
        return articles;
    }
}

