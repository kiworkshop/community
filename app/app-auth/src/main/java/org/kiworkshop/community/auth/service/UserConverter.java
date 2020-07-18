package org.kiworkshop.community.auth.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.auth.dto.SocialResourceResponseDto;
import org.kiworkshop.community.auth.dto.UserDto;
import org.kiworkshop.community.auth.model.Social;
import org.kiworkshop.community.auth.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {
  public static UserDto toDto(User user, PasswordEncoder passwordEncoder) {
    return UserDto.builder()
        .authorities(user.getAuthorities())
        .username(user.getUsername())
        .password(passwordEncoder.encode(user.getSocialId()))
        .accountNonExpired(user.isAccountNonExpired())
        .accountNonLocked(user.isAccountNonLocked())
        .credentialsNonExpired(user.isCredentialsNonExpired())
        .enabled(user.isEnabled())
        .build();
  }

  public static User toEntity(SocialResourceResponseDto dto, String provider) {
    return User.builder()
        .social(Social.builder()
            .provider(Social.Provider.valueOf(provider))
            .socialId(dto.getSocialId())
            .build())
        .build();
  }
}
