package community.auth.api.dto;

import community.auth.model.Social;
import lombok.Getter;

@Getter
public class SignUpDto implements SocialResourceReqeustDto {
  private Social.Provider provider;
  private String providerAccessToken;
}
