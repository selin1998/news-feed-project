package com.feed.news.entity;

import com.feed.news.validation.PasswordsEqualConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@PasswordsEqualConstraint(message = "passwords are not equal")
@Table (name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int user_id;

    @Column(name="full_name")
    private String full_name;

    @Column(name = "email" ,unique = true)
    @Email(message = "*Please provide a valid Email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String password;

    @Column(name = "confirm_password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String confirm_password;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "active")
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @ManyToMany(mappedBy = "users")
    private Set<News> news;


}
