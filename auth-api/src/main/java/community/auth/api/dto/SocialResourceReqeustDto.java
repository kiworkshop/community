package community.auth.api.dto;

import community.auth.model.Social;

public interface SocialResourceReqeustDto {
  Social.Provider getProvider();

  String getProviderAccessToken();
}
