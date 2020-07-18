package org.kiworkshop.community.common.dto;

import static java.time.ZonedDateTime.now;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiError {
  private ZonedDateTime timestamp;
  private int status;
  private String error;
  private String message;

  @Builder
  private ApiError(int status, String error, String message) {
    this.timestamp = now();
    this.status = status;
    this.error = error;
    this.message = message;
  }
}
