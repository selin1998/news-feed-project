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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DroidLifeParser extends RestTemplateService implements JsoupParser {

    List<Article> articles;
    Document doc;

    public DroidLifeParser() {
        articles = new ArrayList();
        doc = rootPage("https://www.droid-life.com/");
    }

    @Override
    public List<Article> getArticles() {
        //  Document document = Jsoup.connect("https://www.droid-life.com/").get();
        Elements elements = doc.getElementsByTag("article");
        for (Element element : elements) {
            String header = element.select(".preview__title").first()
                    .getElementsByTag("a").first().text();
            String content = element.select(".preview__excerpt").text();
            String link = element.getElementsByTag("a").first().attr("href");
            String imageLink = "https://www.droid-life.com/"
                    .concat(element.getElementsByTag("img").first().attr("src"));
            LocalDate date = convertStringToDate(element.select(".entry-meta__updated").text(), DateTimeFormats.FULL_MONTH_FORMAT);

            articles.add(new Article(header, content, link, imageLink, date, Website.DroidLife));
        }

        return articles;
    }
}
