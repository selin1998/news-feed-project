package com.feed.news.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonBackReference
    @ManyToMany(mappedBy = "users" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<News> news;

    public Set<News> getNews(){
        return news;
    }

    public User(String full_name, String user_email, String password,Set<News> news) {
        this.full_name=full_name;
        this.user_email=user_email;
        this.password=password;
        this.news=news;

    }
}
