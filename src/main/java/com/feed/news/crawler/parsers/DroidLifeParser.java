package com.feed.news.crawler.parsers;

import com.feed.news.crawler.Article;
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
import java.util.Optional;

public class DroidLifeParser  implements JsoupParser {
    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.droid-life.com/").get();
        Elements elements = document.getElementsByTag("article");
        for (Element element : elements) {
            String header = element.select(".preview__title").first()
                    .getElementsByTag("a").first().text();
            String content = element.select(".preview__excerpt").text();
            String link =element
//                    .select("preview__link").first()
                    .getElementsByTag("a").first().attr("href");
            String imageLink ="https://www.droid-life.com/"
                    .concat(element.getElementsByTag("img").first().attr("src"));  //.wp-post-image
            LocalDate date = convertStringToDate(element.getElementsByTag("time").first().text(), DateTimeFormats.FULL_MONTH_FORMAT);

            articles.add(new Article(header, content, link, imageLink, date, Website.DroidLife));
        }

        return articles;
    }
}
