package community.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthenticationProvider authenticationProviderImpl;
  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security
        .httpBasic().disable()
        .authorizeRequests()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .csrf()
        .ignoringAntMatchers("/h2-console/**")
        .and()
        .headers()
        .frameOptions().sameOrigin()
    ;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .authenticationProvider(authenticationProviderImpl)
        .userDetailsService(userDetailsService);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}