package org.kiworkshop.community.user.resource.domain.model;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.UUID;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserResourceTest {
  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    // when
    String username = UUID.randomUUID().toString();
    UserResource userResource = UserResource.builder()
        .userId(1L)
        .username(username)
        .nickname("nickname")
        .contactEmail("foo@bar.com").build();

    // then
    then(userResource).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt");
    then(userResource.getUserId()).isEqualTo(1L);
    then(userResource.getNickname()).isEqualTo("nickname");
    then(userResource.getContactEmail()).isEqualTo("foo@bar.com");
    then(userResource.getUsername()).isEqualTo(username);

    then(validator.validate(userResource)).isEmpty();
  }
}
