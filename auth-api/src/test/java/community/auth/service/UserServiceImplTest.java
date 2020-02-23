package community.auth.service;

import static community.auth.model.UserTest.getUserFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import community.auth.model.User;
import community.auth.model.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  private UserServiceImpl userServiceImpl;

  private @Mock UserRepository userRepository;
  private @Mock PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  void loadUserByUsername_ValidInput_ValidOutput() {
    // given
    User user = getUserFixture();
    given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
    given(passwordEncoder.encode(eq(user.getSocialId()))).willReturn("encrypted socialId");

    // expect
    UserDetails result = userServiceImpl.loadUserByUsername("uuid");

    then(result.getPassword()).isEqualTo("encrypted socialId");
    then(result.getAuthorities()).isEqualTo(user.getAuthorities());
    then(result.getUsername()).isEqualTo(user.getUsername());
    then(result.isAccountNonExpired()).isEqualTo(user.isAccountNonExpired());
    then(result.isAccountNonLocked()).isEqualTo(user.isAccountNonLocked());
    then(result.isCredentialsNonExpired()).isEqualTo(user.isCredentialsNonExpired());
    then(result.isEnabled()).isEqualTo(user.isEnabled());
  }

  @Test
  void loadUserByUsername_NonExistentUsername_UsernameNotFoundException() {
    // given
    given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());

    // expect
    thenThrownBy(() -> userServiceImpl.loadUserByUsername("uuid"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("uuid");
  }
}
