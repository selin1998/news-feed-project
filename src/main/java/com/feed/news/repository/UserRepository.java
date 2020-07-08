package com.feed.news.repository;

import com.feed.news.entity.db.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<XUser,Integer> {
     Optional<XUser> findByEmail(String user_email);

     @Modifying
     @Query("update XUser u set u.password = :password where u.user_id = :id")
     void updatePassword(@Param("password") String password, @Param("id") Integer id);

}
