package community.auth.model;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserTest {

  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // given
    String username = UUID.randomUUID().toString();

    // when
    User user = User.builder().username(username).password("password").build();

    // then
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    then(violations.size()).isEqualTo(0);

    then(user.getAuthorities()).containsExactly(Authority.USER);
    then(user.getId()).isNull();
    then(user.getPassword()).isEqualTo("password");
    then(user.getUsername()).isEqualTo(username);
    then(user.isAccountNonExpired()).isTrue();
    then(user.isAccountNonLocked()).isTrue();
    then(user.isCredentialsNonExpired()).isTrue();
    then(user.isEnabled()).isTrue();
  }

  @Test
  void build_UsernameIsNotUUID_ConstraintViolation() {
    User user = User.builder().username("username").password("password").build();

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    then(violations.size()).isEqualTo(1);
  }
}