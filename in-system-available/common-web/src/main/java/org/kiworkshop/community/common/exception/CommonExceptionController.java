package org.kiworkshop.community.common.exception;

import java.util.stream.Collectors;
import org.kiworkshop.community.common.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommonExceptionController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ApiError handleNotFound(RuntimeException e) {
    return new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleInvalidParam(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage() + ".")
        .collect(Collectors.joining("\n"));

    return new ApiError(HttpStatus.BAD_REQUEST, message);
  }
}
