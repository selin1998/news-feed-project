package com.feed.news.crawler.parsers;

import com.feed.news.crawler.RestTemplateService;
import com.feed.news.entity.Article;
import com.feed.news.crawler.DateTimeFormats;
import com.feed.news.crawler.JsoupParser;
import com.feed.news.crawler.Website;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LenovaNewsParser extends RestTemplateService implements JsoupParser {

    List<Article> articles = new ArrayList<>();;
    Document doc;

//    public LenovaNewsParser() {
//        articles = new ArrayList();
//        doc = rootPage("https://news.lenovo.com/pressroom/press-releases/");
//    }

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
          Document doc = Jsoup.connect("https://news.lenovo.com/pressroom/press-releases/").get();
        Elements elements = doc.getElementsByClass("col-12_xs-12");
        for (Element element : elements) {
            String header = element.select(".card-title").text();
            String content = element.select(".card-text").text();
            String link = element.select(".card-title").first().select("a").attr("href");
            String image = element.select(".card-image").first().select("img").attr("src");
            LocalDate date = convertStringToDate(element.select(".card-date").text(), DateTimeFormats.SIMPLE_MONTH_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.LenovaNews));

        }
        return articles;
    }
}

