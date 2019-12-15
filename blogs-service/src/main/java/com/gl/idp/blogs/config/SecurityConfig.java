package com.gl.idp.blogs.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().csrf().disable()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/h2/**").permitAll();
    }
}


