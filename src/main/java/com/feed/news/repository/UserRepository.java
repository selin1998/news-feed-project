package com.feed.news.repository;

import com.feed.news.entity.db.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<XUser,Integer> {
     Optional<XUser> findByEmail(String user_email);
}
