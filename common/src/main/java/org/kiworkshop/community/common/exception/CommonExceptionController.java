package org.kiworkshop.community.common.exception;

import java.util.stream.Collectors;
import org.kiworkshop.community.common.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommonExceptionController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ApiError handleNotFound(RuntimeException e) {
    return ApiError.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(e.getMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleInvalidParam(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage() + ".")
        .collect(Collectors.joining("\n"));


    return ApiError.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(message)
        .build();
  }
}
