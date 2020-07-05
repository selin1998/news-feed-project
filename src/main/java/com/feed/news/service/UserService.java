package com.feed.news.service;


import com.feed.news.entity.XUser;

import java.util.Optional;

public interface UserService {
    public Optional<XUser> findUserByEmail(String user_email);
    public XUser saveUser(XUser user);
}
