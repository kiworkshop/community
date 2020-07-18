package org.kiworkshop.community.auth.dto;

public interface SocialResourceRequestDto {
  String getProvider();

  String getProviderAccessToken();
}
