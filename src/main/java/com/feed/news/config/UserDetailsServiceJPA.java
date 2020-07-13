package com.feed.news.config;


import com.feed.news.entity.db.XUser;
import com.feed.news.security.XUserDetails;
import com.feed.news.repository.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
@Configuration
public class UserDetailsServiceJPA implements UserDetailsService {

  private final UserRepo repo;

  public UserDetailsServiceJPA(UserRepo repo) {
    this.repo = repo;
  }

  public static UserDetails mapper_to_standard_ud(XUser user) {
    return User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .build();
  }

  public static UserDetails mapper_to_xUserDetails(XUser user) {
    return new XUserDetails(
        user.getUser_id(),
        user.getEmail(),
        user.getPassword()
    );
  }

  @Override
  public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
    log.info(String.format(">>>> loading user details for user: %s", user_email));
    return repo.findByEmail(user_email)
        .map(UserDetailsServiceJPA::mapper_to_xUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(
           String.format("User: %s isn't found in our DB", user_email)
        ));
  }

}
