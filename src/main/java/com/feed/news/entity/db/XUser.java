package com.feed.news.entity.db;

import com.feed.news.validation.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "ACTIVE")
    private int active;

    @ManyToMany(cascade= CascadeType.MERGE)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<News> news;

    public XUser(String full_name, String email, String password, Set<News> news) {
        this.full_name=full_name;
        this.email=email;
        this.password=password;
        this.news=news;
    }

    public XUser(String full_name,  String email, String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
    }


    public XUser(String full_name, String email,String password, String confirm_password,Set<Role> roles, Integer active) {
        this.full_name=full_name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.roles = roles;
        this.active = active;
    }

}
