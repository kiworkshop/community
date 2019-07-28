package community.common.dto;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {
  @Test
  void constructApiError_ValidInput_ConstructedApiError() {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "message");

    then(apiError) .hasNoNullFieldsOrProperties();
    then(apiError.getTimestamp()).isNotNull();
    then(apiError.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    then(apiError.getError()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
    then(apiError.getMessage()).isEqualTo("message");
  }
}
