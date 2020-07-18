package org.kiworkshop.community.common.dto;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {
  @Test
  void constructApiError_ValidInput_ConstructedApiError() {
    ApiError apiError = ApiError.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message("message")
        .build();


    then(apiError).hasNoNullFieldsOrProperties();
    then(apiError.getTimestamp()).isNotNull();
    then(apiError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    then(apiError.getError()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
    then(apiError.getMessage()).isEqualTo("message");
  }
}
