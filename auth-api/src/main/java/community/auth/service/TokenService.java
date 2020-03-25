package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import reactor.core.publisher.Mono;

public interface TokenService {
  Mono<AuthenticationDto> getToken(String username, String password);
}
