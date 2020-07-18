package org.kiworkshop.community.auth.service;

import org.kiworkshop.community.auth.api.dto.AuthenticationDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.api.dto.TokenRefreshDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {
  Mono<AuthenticationDto> signUp(SocialResourceRequestDto socialResourceRequestDto);

  Mono<AuthenticationDto> signIn(SocialResourceRequestDto socialResourceRequestDto);

  Mono<Void> signOut(TokenRefreshDto tokenRefreshDto);

  Mono<AuthenticationDto> tokenRefresh(TokenRefreshDto tokenRefreshDto);
}
