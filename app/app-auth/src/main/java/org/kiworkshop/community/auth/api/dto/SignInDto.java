package org.kiworkshop.community.auth.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.kiworkshop.community.auth.model.Social;

@Getter
public class SignInDto implements SocialResourceRequestDto {
  private @NotNull Social.Provider provider;
  private @NotEmpty String providerAccessToken;
}
