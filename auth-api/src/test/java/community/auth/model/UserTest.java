package community.auth.model;

import static community.res.model.SocialTest.getSocialFixture;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class UserTest {

  public static User getUserFixture() {
    User user = User.builder()
        .social(getSocialFixture()).build();

    ReflectionTestUtils.setField(user, "id", 1L);

    return user;
  }

  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // when
    User user = User.builder().social(getSocialFixture()).build();

    // then
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    then(violations.size()).isEqualTo(0);

    then(user.getAuthorities()).containsExactly(Authority.USER);
    then(user.getId()).isNull();
    then(user.getUsername()).isNotEmpty();
    then(user.isAccountNonExpired()).isTrue();
    then(user.isAccountNonLocked()).isTrue();
    then(user.isCredentialsNonExpired()).isTrue();
    then(user.isEnabled()).isTrue();
  }

  @Test
  void build_UsernameIsNotUuid_ConstraintViolation() {
    User user = User.builder().social(getSocialFixture()).build();

    Set<ConstraintViolation<User>> violations = validator.validate(user);

    then(violations.size()).isEqualTo(1);
  }
}
