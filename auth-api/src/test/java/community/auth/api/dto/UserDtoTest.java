package community.auth.api.dto;

import static community.auth.model.UserTest.getUserFixture;

import community.auth.config.Beans;
import community.auth.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDtoTest {
  public static UserDto getUserDtoFixture() {
    User user = getUserFixture();
    PasswordEncoder passwordEncoder = new Beans().passwordEncoder();
    return UserDto.from(user, passwordEncoder);
  }
}
