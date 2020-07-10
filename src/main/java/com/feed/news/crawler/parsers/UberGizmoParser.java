package com.feed.news.crawler.parsers;

import com.feed.news.crawler.RestTemplateService;
import com.feed.news.entity.Article;
import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UberGizmoParser extends RestTemplateService implements JsoupParser {

    List<Article> articles;
    Document doc;

    public UberGizmoParser() {
        this.articles = new ArrayList<>();
        this.doc = rootPage("https://www.ubergizmo.com/");
    }

    @Override
    public List<Article> getArticles() {
        //  Document document = Jsoup.connect("https://www.ubergizmo.com/").get();
        Elements elements = doc.getElementsByClass("article_card");
        for (Element element : elements) {
            String header = element.select(".article_card_title").text();
            String content = element.select(".article_card_excerpt").text();
            String link = element.select(".article_card_title").first().select("a").first().attr("href");
            String image = element.select(".article_card_divimg").first().attr("data-bg");
            LocalDate date = convertStringToDate(element.select(".byline").text().split(", on")[1], DateTimeFormats.PDT_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.UberGizmo));

        }
        return articles;
    }
}

