package com.feed.news.crawler;

import com.feed.news.crawler.parsers.*;

public enum Website {

    TechCrunch(new TechCrunchParser()),
    UberGizmo(new UberGizmoParser()),
    DroidLife(new DroidLifeParser()),
    TechStartups(new TechStartupsParser()),
    Lenova(new LenovaParser()),
    DigitIn(new DigitInParser()),
    Policy(new PolicyParser()),
    Insider(new InsiderParser()),
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
