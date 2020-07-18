package org.kiworkshop.community.comment.dto;

import static org.assertj.core.api.BDDAssertions.then;

import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

class CommentRequestDtoTest {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private CommentRequestDto commentRequestDto;

  @BeforeEach
  void setUp() {
    commentRequestDto = CommentRequestDtoFixture.get();
  }

  @ParameterizedTest
  @CsvSource({
      "boardType,",
      "postId,",
      "username,empty",
      "content,empty",
  })
  void validate_InvalidValue_ThrowException(String fieldName, String value) {
    // given
    String realValue = "empty".equals(value) ? "" : value;
    ReflectionTestUtils.setField(commentRequestDto, fieldName, realValue);

    // when
    var violations = validator.validate(commentRequestDto);

    // then
    then(violations.size()).isEqualTo(1);
  }
}