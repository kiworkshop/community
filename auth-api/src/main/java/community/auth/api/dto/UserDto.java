package community.auth.api.dto;

import community.auth.model.User;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDto implements UserDetails {
  public static UserDto from(User user, PasswordEncoder passwordEncoder) {
    UserDto userDto = new UserDto();
    userDto.authorities = user.getAuthorities();
    userDto.username = user.getUsername();
    userDto.password = passwordEncoder.encode(user.getSocialId());
    userDto.accountNonExpired = user.isAccountNonExpired();
    userDto.accountNonLocked = user.isAccountNonLocked();
    userDto.credentialsNonExpired = user.isCredentialsNonExpired();
    userDto.enabled = user.isEnabled();

    return userDto;
  }

  private @Getter Collection<? extends GrantedAuthority> authorities;

  private @Getter String username;
  private @Getter String password;

  private @Getter boolean accountNonExpired;
  private @Getter boolean accountNonLocked;
  private @Getter boolean credentialsNonExpired;
  private @Getter boolean enabled;
}
