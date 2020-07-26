package org.kiworkshop.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class MonolithWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
  }
}

