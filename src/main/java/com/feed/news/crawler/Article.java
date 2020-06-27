package com.feed.news.crawler;


import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Article {
    String header;
    String content;
    String articleLink;
    String imageLink;
    String date;

}
