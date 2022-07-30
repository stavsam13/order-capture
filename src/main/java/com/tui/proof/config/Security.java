package com.tui.proof.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class Security  {
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");
    }
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter  {
         protected void configure (HttpSecurity http) throws Exception {
             http.
                     csrf().disable().
                     authorizeRequests()
                     .antMatchers("tuiTest/createPillotes").permitAll()
                .antMatchers("/tuiTest/updatePillotes").permitAll()
                .antMatchers("/tuiTest/searchPillotes")
                .hasRole("USER").and().httpBasic();
//                     .
        }
    }
}
