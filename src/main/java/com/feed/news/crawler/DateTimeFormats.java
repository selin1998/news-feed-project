package com.feed.news.crawler;

import java.time.format.DateTimeFormatter;

public class DateTimeFormats {
    public final DateTimeFormatter ABBR_MONTH_FORMAT=DateTimeFormatter.ofPattern("MMM dd, uuuu");
    public final DateTimeFormatter SIMPLE_MONTH_FORMAT=DateTimeFormatter.ofPattern("d MMMM uuuu");
    public final DateTimeFormatter FULL_MONTH_FORMAT=DateTimeFormatter.ofPattern("MMMM d, uuuu");
    public final DateTimeFormatter HABR_FORMAT=DateTimeFormatter.ofPattern("MMMM d, uuuu 'at' hh:mm a");
    public final DateTimeFormatter ISO_INSTANT_FORMAT=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public final DateTimeFormatter PDT_FORMAT=DateTimeFormatter.ofPattern(" MM/dd/uuuu HH:mm zzz");
    public final DateTimeFormatter POLICY_FORMAT=DateTimeFormatter.ofPattern("MMMM d['st']['nd']['rd']['th'], uuuu");

}
