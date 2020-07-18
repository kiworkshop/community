package org.kiworkshop.community.auth.api.dto;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
public class UserDto implements UserDetails {
  private @Getter Collection<? extends GrantedAuthority> authorities;

  private @Getter String username;
  private @Getter String password;

  private @Getter boolean accountNonExpired;
  private @Getter boolean accountNonLocked;
  private @Getter boolean credentialsNonExpired;
  private @Getter boolean enabled;

  @Builder
  private UserDto(
      Collection<? extends GrantedAuthority> authorities,
      String username,
      String password, // encoded
      boolean accountNonExpired,
      boolean accountNonLocked,
      boolean credentialsNonExpired,
      boolean enabled
  ) {
    this.authorities = authorities;
    this.username = username;
    this.password = password;
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
    this.enabled = enabled;
  }
}
