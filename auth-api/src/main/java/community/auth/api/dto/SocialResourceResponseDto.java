package community.auth.api.dto;

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
}
