//package com.feed.news.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Data
//@Entity
//@Table (name="news")
//public class News {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column (name = "news_id")
//    private int news_id;
//
//    @Column (name = "news_url")
//    private String news_url;
//
//    @ManyToMany
//    @JoinTable(name = "disliked",
//    joinColumns = {@JoinColumn(name = "n_id", referencedColumnName ="news_id" )},
//            inverseJoinColumns = {@JoinColumn(name = "u_id", referencedColumnName = "user_id")}
//    )
//    private Set<User> users;
//}
