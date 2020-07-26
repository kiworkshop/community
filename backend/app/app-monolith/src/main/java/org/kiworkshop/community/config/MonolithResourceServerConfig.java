package org.kiworkshop.community.config;

import org.kiworkshop.community.common.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class MonolithResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/user-resources/me").authenticated()
        .antMatchers(HttpMethod.GET, "/notices").permitAll()
        .antMatchers(HttpMethod.GET, "/notices/{\\d}").permitAll()
      .and()
        .authorizeRequests()
        .antMatchers("/**").hasAuthority(Role.ROLE_ADMIN.name())
    ;
  }
}
