package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.model.User;
import reactor.core.publisher.Mono;

public interface TokenService {
  Mono<AuthenticationDto> getTokenOf(User user);

  Mono<AuthenticationDto> refresh(String refreshToken);
}
