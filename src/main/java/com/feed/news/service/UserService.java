package com.feed.news.service;


import com.feed.news.entity.User;

public interface UserService {
    public User findUserByEmail(String user_email);
    public User saveUser(User user);
}
