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

public class PolicyParser  implements JsoupParser {

    List<Article> articles = new ArrayList<>();;
    Document doc;


    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document doc = connection("https://www.policygenius.com/blog/");
        Elements elements = doc.getElementsByClass("teaser");
        for (Element element : elements) {
            String header = element.select(".teaser__title").text();
            String content = header;
            String link = String.format("%s%s", "https://www.policygenius.com", element.select(".teaser__title").select("a").attr("href"));
            String image = element.select(".picture__image").attr("data-src");
            LocalDate date = convertStringToDate(element.getElementsByTag("time").text(), dateTimeForm.POLICY_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.Policy));

        }
        return articles;
    }

}
