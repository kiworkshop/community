package org.kiworkshop.community.auth.api.dto;

import static org.kiworkshop.community.auth.model.UserTest.getUserFixture;

import org.kiworkshop.community.auth.config.Beans;
import org.kiworkshop.community.auth.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDtoTest {
  public static UserDto getUserDtoFixture() {
    User user = getUserFixture();
    PasswordEncoder passwordEncoder = new Beans().passwordEncoder();
    return UserDto.from(user, passwordEncoder);
  }
}
