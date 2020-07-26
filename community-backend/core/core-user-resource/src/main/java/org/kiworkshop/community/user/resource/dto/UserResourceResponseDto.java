package org.kiworkshop.community.user.resource.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResourceResponseDto {
  private String username;
  private String nickname;
  private String contactEmail;
}
