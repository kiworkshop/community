package org.kiworkshop.community.article.dto;

import static org.assertj.core.api.BDDAssertions.then;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

class ArticleRequestDtoTest {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private ArticleRequestDto articleRequestDto;

    @BeforeEach
    void setUp() {
        articleRequestDto = ArticleRequestDtoFixture.get();
    }

    @ParameterizedTest
    @CsvSource({
        "title,empty",
        "content,empty",
        "username,empty"
    })
    void validate_InvalidValue_ThrowException(String fieldName, String value) {
        // given
        String valueToInject = "empty".equals(value) ? "" : value;
        ReflectionTestUtils.setField(articleRequestDto, fieldName, valueToInject);

        var violations = validator.validate(articleRequestDto);

        then(violations.size()).isEqualTo(1);
    }
}
