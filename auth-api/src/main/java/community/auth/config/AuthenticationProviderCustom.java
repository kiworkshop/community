package community.auth.config;

import community.auth.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderCustom implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) {
    UserDetails userInfo = userService.loadUserByUsername(authentication.getPrincipal().toString());

    if (!matchPassword(authentication.getCredentials().toString(), userInfo.getPassword())) {
      throw new BadCredentialsException("not matching username or password");
    }

    Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userInfo.getAuthorities();

    return new UsernamePasswordAuthenticationToken(userInfo,null,authorities);
  }

  private boolean matchPassword(String credentials, String password) {
    return passwordEncoder.matches(credentials, password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}