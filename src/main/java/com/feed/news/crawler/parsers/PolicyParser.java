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

public class PolicyParser extends RestTemplateService implements JsoupParser {

    List<Article> articles;
    Document doc;

    public PolicyParser() {
        this.articles = new ArrayList();
        doc = rootPage("https://www.policygenius.com/blog/");
    }

    @Override
    public List<Article> getArticles() {
        //  Document document = Jsoup.connect("https://www.policygenius.com/blog/").get();
        Elements elements = doc.getElementsByClass("teaser");
        for (Element element : elements) {
            String header = element.select(".teaser__title").text();
            String content = header;
            String link = String.format("%s%s", "https://www.policygenius.com", element.select(".teaser__title").select("a").attr("href"));
            String image = element.select(".picture__image").attr("data-src");
            LocalDate date = convertStringToDate(element.getElementsByTag("time").text(), DateTimeFormats.POLICY_FORMAT);

            articles.add(new Article(header, content, link, image, date, Website.Policy));

        }
        return articles;
    }

}
