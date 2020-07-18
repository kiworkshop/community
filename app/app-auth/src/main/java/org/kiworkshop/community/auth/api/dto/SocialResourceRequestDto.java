package org.kiworkshop.community.auth.api.dto;

import org.kiworkshop.community.auth.model.Social;

public interface SocialResourceRequestDto {
  Social.Provider getProvider();

  String getProviderAccessToken();
}
