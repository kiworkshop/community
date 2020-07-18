package org.kiworkshop.community.user.resource.domain.model;

import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserResourceTest {

  public static UserResource getUserResourceFixture() {
    UserResource userResource = UserResource.builder()
        .userId(1L)
        .nickname("nickname")
        .contactEmail("foo@bar.com").build();

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
        .userId(1L)
        .nickname("nickname")
        .contactEmail("foo@bar.com").build();

    // then
    then(userResource).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt");
    then(userResource.getUserId()).isEqualTo(1L);
    then(userResource.getNickname()).isEqualTo("nickname");
    then(userResource.getContactEmail()).isEqualTo("foo@bar.com");

    then(validator.validate(userResource)).isEmpty();
  }
}
