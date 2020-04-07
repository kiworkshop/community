package community.auth.api.dto;

import community.auth.model.Social;
import community.auth.model.User;
import lombok.Builder;
import lombok.Getter;

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
