package com.feed.news.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table (name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int user_id;

    @Column(name="full_name")
    private String full_name;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "users")
    private Set<News> news;
}
