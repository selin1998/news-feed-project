package com.feed.news.crawler;

import java.time.format.DateTimeFormatter;

public class DateTimeFormats {
    public static final DateTimeFormatter ABBR_MONTH_FORMAT=DateTimeFormatter.ofPattern("MMM dd, uuuu");
    public static final DateTimeFormatter SIMPLE_MONTH_FORMAT=DateTimeFormatter.ofPattern("d MMMM uuuu");
    public static final DateTimeFormatter SIMPLE_MONTH_FORMAT2=DateTimeFormatter.ofPattern("MMMM d, uuuu");
    public static final DateTimeFormatter FULL_MONTH_FORMAT=DateTimeFormatter.ofPattern("MMMM dd, uuuu");
    public static final DateTimeFormatter HABR_FORMAT=DateTimeFormatter.ofPattern("MMMM dd, uuuu 'at' hh:mm a");
    public static final DateTimeFormatter ISO_INSTANT_FORMAT=DateTimeFormatter.ofPattern("MMM d, uuuu, hh:mm a");
    public static final DateTimeFormatter PDT_FORMAT=DateTimeFormatter.ofPattern(" MM/dd/uuuu HH:mm zzz");
    public static final DateTimeFormatter POLICY_FORMAT=DateTimeFormatter.ofPattern("MMMM d['st']['nd']['rd']['th'], uuuu");

}
