package com.feed.news.crawler.parsers;

import com.feed.news.crawler.*;
import com.feed.news.entity.Article;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TechCrunchParser extends RestTemplateService implements JsoupParser {

    List<Article> articles;
    Document doc;

    public TechCrunchParser() {
        this.articles = new ArrayList();
        this.doc = rootPage("https://techcrunch.com/");
    }

    @Override
    public List<Article> getArticles() {
        //    Document document = Jsoup.connect("https://techcrunch.com/").get();
        Elements elements = doc.getElementsByClass("post-block");
        for (Element element : elements) {
            String header = element.select(".post-block__title__link").text();
            String content = element.select(".post-block__content").text();
            String link = element.select(".post-block__title").first().select("a").first().attr("href");
            String image = element.select(".post-block__media").first().select("img").first().attr("src");
            LocalDate date = convertStringToDate(element.select("[datetime]").text(), DateTimeFormats.ABBR_MONTH_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.TechCrunch));

        }
        return articles;
    }


}
