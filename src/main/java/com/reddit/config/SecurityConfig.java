package com.reddit.config;


import com.reddit.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userProfileService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .httpBasic().and()
                .authorizeRequests()
                    .antMatchers("/", "index.html", "/home", "home.html", "/pop", "/user", "/check").permitAll()
                    .antMatchers("/top-posts", "/top-comment", "/like/**", "/add-comment/**").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/admin/**").authenticated()
                    .antMatchers("/top-posts", "/top-comment").authenticated()
                .and()
                    .exceptionHandling().accessDeniedPage("/error/403")
                .and()
                    .csrf().disable();
    }
}















