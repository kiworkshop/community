package org.kiworkshop.community.auth.config;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) {
    UserDetails userInfo = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

    if (!matchPassword(authentication.getCredentials().toString(), userInfo.getPassword())) {
      throw new BadCredentialsException("not matching username or password");
    }

    Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();

    return new UsernamePasswordAuthenticationToken(userInfo,null, authorities);
  }

  private boolean matchPassword(String credentials, String password) {
    return passwordEncoder.matches(credentials, password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}