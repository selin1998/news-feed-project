package com.feed.news.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;



public class RestTemplateService {


    private  RestTemplate rest;


    public  RestTemplateService() {

        this.rest = new RestTemplate();
    }

    private  HttpHeaders pretend_browser() {
        return new HttpHeaders() {{
            add("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        }};
    }

    private   String rootPageFromWeb(String url) {
        HttpEntity<Object> rqEntity = new HttpEntity<>(pretend_browser());
        return rest.exchange(url, HttpMethod.GET, rqEntity, String.class).getBody();
    }

    public  Document rootPage(String url){
        return Jsoup.parse(rootPageFromWeb(url));
    }

}
