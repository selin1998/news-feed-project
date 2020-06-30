package com.feed.news.crawler;

import com.feed.news.crawler.parsers.*;

public enum Website {

    TechCrunch(new TechCrunchParser()),
    UberGizmo(new UberGizmoParser()),
    DroidLife(new DroidLifeParser()),
    TechStartups(new TechStartupsParser()),
    LenovaNews(new LenovaNewsParser()),
    HTCNews(new HTCNewsParser()),
    DigitIn(new DigitInParser()),
    Insider(new InsiderParser()),
    Policy(new PolicyParser()),
    Habr(new HabrParser());
    // Mashable(new MashableParser());
    //   BBC(new BBCParser()),
    //   TheNextWeb(new TheNextWebParser()),

    public final JsoupParser parser;

    Website(JsoupParser parser) {
        this.parser = parser;
    }

    public JsoupParser getParser() {
        return this.parser;
    }
}
