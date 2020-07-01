package com.feed.news.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name="news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "news_id")
    private int news_id;

    @Column (name = "news_name")
    private String news_name;

    @Column (name = "news_url")
    private String news_url;

    @Column(name = "news_description")
    private String news_description;

    @Column(name = "news_image")
    private byte[] news_image;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "disliked",
    joinColumns = {@JoinColumn(name = "n_id", referencedColumnName ="news_id" )},
            inverseJoinColumns = {@JoinColumn(name = "u_id", referencedColumnName = "user_id")}
    )
    private Set<User> users;


    public News(String news_name, String news_url
            ,String news_description,byte[] news_image
    ) {
        this.news_name=news_name;
        this.news_url=news_url;
        this.news_description=news_description;
        this.news_image=news_image;
    }

    public String getImageAsBase64() {
        byte[] encoded = Base64.getEncoder().encode(news_image);
        String imgDataAsBase64 = new String(encoded);
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        return imgAsBase64;
    }
}
