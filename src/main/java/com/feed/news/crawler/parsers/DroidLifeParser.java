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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DroidLifeParser  implements JsoupParser {

    static List<Article> articles = new ArrayList<>();;
    Document doc;

    @SneakyThrows
   // @Override
    public  List<Article> getArticles() {
        Document doc = connection("https://www.droid-life.com/");
        Elements elements = doc.getElementsByTag("article");
        for (Element element : elements) {
            String header = element.select(".preview__title").text();
            String content = element.select(".preview__excerpt").text();
            String link = element.select(".preview__link").first().select("a").first().attr("href");
            String imageLink = "https://www.droid-life.com/"
                    .concat(element.select(".picture").first().select("img").first().attr("data-src"));
            LocalDate date = convertStringToDate(element.select(".entry-meta__updated").text(), dateTimeForm.FULL_MONTH_FORMAT);

            articles.add(new Article(header, content, link, imageLink, date, Website.DroidLife));
        }

        return articles;
    }

}
