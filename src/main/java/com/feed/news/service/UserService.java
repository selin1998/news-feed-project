package com.feed.news.service;


import com.feed.news.entity.db.XUser;
import com.feed.news.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder crypt) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = crypt;
    }

    public Optional<XUser> findUserByEmail(String user_email){
       return userRepo.findByEmail(user_email);
    }

    public XUser saveUser(XUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirm_password(bCryptPasswordEncoder.encode(user.getConfirm_password()));
        user.setActive(1);
        return userRepo.save(user);
    }

    public void updatePassword(String password,String confirm_password, Integer userId){
        userRepo.updatePassword(password, confirm_password, userId);
    }
}
