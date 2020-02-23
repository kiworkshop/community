package community.res.model;

import static community.res.model.SocialTest.getSocialFixture;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.UUID;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserResourceTest {

  public static UserResource getUserResourceFixture() {
    UserResource userResource = UserResource.builder()
        .username(UUID.randomUUID().toString())
        .nickname("nickname")
        .contactEmail("foo@bar.com")
        .social(getSocialFixture()).build();

    ReflectionTestUtils.setField(userResource, "id", 1L);
    ReflectionTestUtils.setField(userResource, "createdAt", now());
    ReflectionTestUtils.setField(userResource, "updatedAt", now());

    return userResource;
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
    UserResource userResource = UserResource.builder()
        .username(UUID.randomUUID().toString())
        .nickname("nickname")
        .contactEmail("foo@bar.com")
        .social(getSocialFixture()).build();

    // then
    then(userResource).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt");
    then(userResource.getUsername()).isNotEmpty();
    then(userResource.getNickname()).isEqualTo("nickname");
    then(userResource.getContactEmail()).isEqualTo("foo@bar.com");
    then(userResource.getSocial()).isNotNull();

    then(validator.validate(userResource)).isEmpty();
  }
}
