package com.feed.news.crawler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Article {
    String header;
    String content;
    String articleLink;
    String imageLink;
    String date;

}
