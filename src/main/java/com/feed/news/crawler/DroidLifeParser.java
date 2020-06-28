package com.feed.news.crawler;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DroidLifeParser extends News implements JsoupParser {
    static List<Article> articles = new ArrayList();

    @SneakyThrows
    @Override
    public List<Article> getArticles() {
        Document document = Jsoup.connect("https://www.droid-life.com/").get();
        Elements elements = document.getElementsByTag("article");  //preview-home
        for (Element element : elements) {
            String header = element.select(".preview__title").first()
                    .getElementsByTag("a").first().text();   //h2->text
            String content = element.select(".preview__excerpt").text();
            String link =element.select("preview__link").first()
                    .getElementsByTag("a").first().attr("href");  //+
            String imageLink ="https://www.droid-life.com/"
                    .concat(element.getElementsByTag("img").first().attr("src"));  //.wp-post-image
            //https://www.droid-life.com/+imageLink  //???
            String date = element.getElementsByTag("time").first().text();  //+

            articles.add(new Article(header, content, link, imageLink, date, Website.DroidLife));
        }

        return articles;
    }
}
