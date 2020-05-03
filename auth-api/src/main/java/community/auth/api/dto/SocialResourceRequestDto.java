package community.auth.api.dto;

import community.auth.model.Social;

public interface SocialResourceRequestDto {
  Social.Provider getProvider();

  String getProviderAccessToken();
}
