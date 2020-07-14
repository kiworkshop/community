package org.kiworkshop.community.auth.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.kiworkshop.community.auth.model.Social;
import org.kiworkshop.community.auth.model.User;

@Getter
public class SocialResourceResponseDto {
  private String socialId;
  private String contactEmail;

  @Builder
  private SocialResourceResponseDto(String socialId, String contactEmail) {
    this.socialId = socialId;
    this.contactEmail = contactEmail;
  }

  public User createUser(Social.Provider provider) {
    return User.builder()
        .social(Social.builder()
            .provider(provider)
            .socialId(socialId)
            .build())
        .build();
  }
}
