package com.feed.news.crawler;

import com.feed.news.crawler.parsers.*;

public enum Website {

    TechCrunch(new TechCrunchParser()),
    Policy(new PolicyParser()),
    Habr(new HabrParser()),
    Insider(new InsiderParser()),
    DigitIn(new DigitInParser()),
    BBC(new BBCParser()),
//    TheNextWeb(new TheNextWebParser()),
//    TechStartups(new TechStartupsParser()),
    UberGizmo(new UberGizmoParser()),
    Mashable(new MashableParser()),
    DroidLife(new DroidLifeParser());

    public final JsoupParser parser;

    Website(JsoupParser parser) {
        this.parser=parser;
    }

    public JsoupParser getParser() {
        return this.parser;
    }
}
