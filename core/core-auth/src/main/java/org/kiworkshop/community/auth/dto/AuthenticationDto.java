package org.kiworkshop.community.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthenticationDto {
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("expires_in")
  private int expiresIn;
  @JsonProperty("scope")
  private String scope;
}
