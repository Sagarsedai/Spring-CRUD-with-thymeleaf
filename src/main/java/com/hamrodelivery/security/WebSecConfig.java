package com.hamrodelivery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//  Bcrypt password encoder is used as password encoding algorithm
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

//    This is inmemoryauthentication scheme
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder)
                .withUser("rajbogati@gmail.com").password(bCryptPasswordEncoder.encode("raj")).authorities("USER")
                .and()
                .withUser("leokingsagarbro@gmail.com").password(bCryptPasswordEncoder.encode("sagar")).authorities("ADMIN")
        ;
    }

//    This is added later by me to allow all methods ignoring of spring security issues
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
    }

//    This is added to disable cors problem ok
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .formLogin()
        .and()
        .logout()
        .logoutSuccessUrl("/login");
    }

}
