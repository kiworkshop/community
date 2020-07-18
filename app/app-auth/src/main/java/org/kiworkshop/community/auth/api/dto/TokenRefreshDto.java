package org.kiworkshop.community.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TokenRefreshDto {
  @JsonProperty("refresh_token")
  private @NotEmpty String refreshToken;
}
