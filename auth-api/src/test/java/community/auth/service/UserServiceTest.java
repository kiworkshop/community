package community.auth.service;

import static community.auth.model.UserTest.getUserFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import community.auth.model.User;
import community.auth.model.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  private UserService userService;

  private @Mock UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userService = new UserService(userRepository);
  }

  @Test
  void loadUserByUsername_ValidInput_ValidOutput() {
    // given
    User user = getUserFixture();
    given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));

    // expect
    then(userService.loadUserByUsername("uuid")).isEqualTo(user);
  }

  @Test
  void loadUserByUsername_NonExistentUsername_UsernameNotFoundException() {
    // given
    given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());

    // expect
    thenThrownBy(() -> userService.loadUserByUsername("uuid"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("uuid");
  }
}
