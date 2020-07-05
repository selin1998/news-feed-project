package com.feed.news.repository;

import com.feed.news.entity.News;
import com.feed.news.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.hibernate.hql.internal.antlr.SqlTokenTypes.LEFT;
import static org.hibernate.hql.internal.antlr.SqlTokenTypes.ON;

@Repository
public interface NewsFeedRepo extends JpaRepository<News,Integer> {

    @Query(value="SELECT news_name FROM news  JOIN disliked ON disliked.n_id = news.news_id WHERE disliked.u_id=:id" , nativeQuery = true)
    List<String> extractbyUserid(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value ="INSERT INTO disliked(u_id,n_id) VALUES(:user_id,:news_id)",nativeQuery=true)
    void addDislike(@Param("user_id")int u_id,@Param("news_id")int n_id);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM disliked WHERE disliked.u_id=:user_id AND  disliked.n_id=:news_id",nativeQuery = true)
    void deleteDislike(@Param("user_id")int u_id,@Param("news_id")int n_id);

    @Query(value="SELECT n_id FROM disliked WHERE disliked.u_id=:user_id", nativeQuery = true)
    List<Integer> getAllDislikes(@Param("user_id")int u_id);

}
