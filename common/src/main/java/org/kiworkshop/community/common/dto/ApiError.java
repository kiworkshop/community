package org.kiworkshop.community.common.dto;

import static java.time.ZonedDateTime.now;

import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
  private ZonedDateTime timestamp;
  private Integer status;
  private String error;
  private String message;

  public ApiError(HttpStatus httpStatus, String message) {
    this.timestamp = now();
    this.status = httpStatus.value();
    this.error = httpStatus.getReasonPhrase();
    this.message = message;
  }
}
