package com.feed.news.service;


import com.feed.news.entity.db.XUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<XUser> findUserByEmail(String user_email);
    XUser saveUser(XUser user);
    void updatePassword(String password,String confirm_password, Integer userId);
}
