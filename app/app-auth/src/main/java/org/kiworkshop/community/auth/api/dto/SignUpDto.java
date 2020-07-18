package org.kiworkshop.community.auth.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpDto implements SocialResourceRequestDto {
  private @NotNull String provider;
  private @NotEmpty String providerAccessToken;
}
