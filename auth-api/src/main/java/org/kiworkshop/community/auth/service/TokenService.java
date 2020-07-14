package org.kiworkshop.community.auth.service;

import org.kiworkshop.community.auth.api.dto.AuthenticationDto;
import org.kiworkshop.community.auth.model.User;
import reactor.core.publisher.Mono;

public interface TokenService {
  Mono<AuthenticationDto> getTokenOf(User user);

  Mono<AuthenticationDto> refresh(String refreshToken);
}
