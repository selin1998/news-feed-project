package com.feed.news.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

@Getter
@Setter
public class XUserDetails implements UserDetails {

  private final int id;
  private final String username;
  private final String password;

  public XUserDetails(int id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return String.format("XUserDetails[%d:'%s':'%s':{%s}]", id, username, password);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


}
