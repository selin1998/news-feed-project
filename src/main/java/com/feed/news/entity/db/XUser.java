package com.feed.news.entity.db;

import com.feed.news.entity.News;
import com.feed.news.entity.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Base64;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table (name = "user_table")
public class XUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int user_id;

    @Column(name="full_name")
    private String full_name;

    @Column(name = "email" ,unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "confirm_password")
    private String confirm_password;


    @Column(name="image")
    private byte[] image;

    @Column(name = "ACTIVE")
    private int active;

    @Transient
    private MultipartFile file;

    @Column(length = 32, columnDefinition = "varchar(32) default 'USER'")
    @Enumerated(EnumType.STRING)
    Role role=Role.USER;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<News> news;

    private boolean isEnabled;

    public String getImageAsBase64() {
        byte[] encoded = Base64.getEncoder().encode(image);
        String imgDataAsBase64 = new String(encoded);
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        return imgAsBase64;
    }

}
