package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SocialResourceRequestDto;
import community.auth.api.dto.TokenRefreshDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {
  Mono<AuthenticationDto> signUp(SocialResourceRequestDto socialResourceRequestDto);

  Mono<AuthenticationDto> signIn(SocialResourceRequestDto socialResourceRequestDto);

  Mono<Void> signOut(TokenRefreshDto tokenRefreshDto);

  Mono<AuthenticationDto> tokenRefresh(TokenRefreshDto tokenRefreshDto);
}
