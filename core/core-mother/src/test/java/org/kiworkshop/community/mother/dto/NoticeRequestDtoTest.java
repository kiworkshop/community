package org.kiworkshop.community.mother.dto;

import static org.assertj.core.api.BDDAssertions.then;

import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

class NoticeRequestDtoTest {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private NoticeRequestDto noticeRequestDto;

  @BeforeEach
  void setUp() {
    noticeRequestDto = NoticeRequestDtoFixture.get();
  }

  @ParameterizedTest
  @CsvSource({
      "title,empty",
      "content,empty"
  })
  void validate_InvalidValue_ThrowException(String fieldName, String value) {
    // given
    String realValue = "empty".equals(value) ? "" : value;
    ReflectionTestUtils.setField(noticeRequestDto, fieldName, realValue);

    // when
    var violations = validator.validate(noticeRequestDto);

    // then
    then(violations.size()).isEqualTo(1);
  }
}
