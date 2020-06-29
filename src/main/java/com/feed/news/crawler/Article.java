package com.feed.news.crawler;


import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

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
    LocalDate date;
    Website site;

}
