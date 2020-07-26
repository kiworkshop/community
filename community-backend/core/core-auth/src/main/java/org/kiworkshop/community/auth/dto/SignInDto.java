package org.kiworkshop.community.auth.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInDto implements SocialResourceRequestDto {
  private @NotNull String provider;
  private @NotEmpty String providerAccessToken;
}
