package org.kiworkshop.community.auth.service;

import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.dto.SignUpDto;
import org.kiworkshop.community.auth.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.dto.TokenRefreshDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {
  Mono<AuthenticationDto> signUp(SignUpDto socialResourceRequestDto);

  Mono<AuthenticationDto> signIn(SocialResourceRequestDto socialResourceRequestDto);

  Mono<Void> signOut(TokenRefreshDto tokenRefreshDto);

  Mono<AuthenticationDto> tokenRefresh(TokenRefreshDto tokenRefreshDto);
}
