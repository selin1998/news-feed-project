package com.feed.news.crawler;


import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {
    static List<Article> articles = new ArrayList();

    @SneakyThrows
    public static void main(String[] args) {
        Document document = Jsoup.connect("https://techcrunch.com/").get();
        Elements elements = document.getElementsByClass("post-block");
        for (Element element : elements) {
            String header = element.select(".post-block__title__link").text();
            String content= element.select(".post-block__content").text();
            String link=element.select(".post-block__title").first().select("a").first().attr("href");
            String image=element.select(".post-block__media").first().select("img").first().attr("src");
            String date=element.select("[datetime]").text();

            articles.add(new Article(header,content,link,image,date));

            System.out.println(header);
            System.out.println(content);
            System.out.println(link);
            System.out.println(image);
            System.out.println(date);

        }


    }

    @GetMapping("/")
    public ModelAndView getNewsArticles(){
        ModelAndView view=new ModelAndView("main-page");
        return view;
    }


}