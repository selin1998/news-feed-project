package com.feed.news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .authoritiesByUsernameQuery(rolesQuery)
                .usersByUsernameQuery(usersQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //Permission only for registered users
                .antMatchers("/registration").permitAll()
                //Permission for all users
                .antMatchers("/", "/resources/**").permitAll()
                //Other sites ask authentication
                .anyRequest().authenticated();
        http
                //Settings for login
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                //Redirects to main page after successful login
                .defaultSuccessUrl("/news")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
        http
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")

                // Settings for OAUTH2
                .and().oauth2Login() .loginPage("/login")// enable OAuth2
                .defaultSuccessUrl("/oauth2LoginSuccess")
                .and().csrf().disable(); // disable CSRF

        http
                .rememberMe()
                .key("myUniqueKey")
                .tokenValiditySeconds(10000000);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/img/**");
    }

 }