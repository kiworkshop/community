package org.kiworkshop.community.auth.api.dto;

public interface SocialResourceRequestDto {
  String getProvider();

  String getProviderAccessToken();
}
