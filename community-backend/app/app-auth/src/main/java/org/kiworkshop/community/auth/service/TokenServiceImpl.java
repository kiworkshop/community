package org.kiworkshop.community.auth.service;

import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TokenServiceImpl implements TokenService {
  private final WebClient webClient;

  public TokenServiceImpl(
      WebClient.Builder webClientBuilder,
      @Value("${service.auth-api}") String authBaseUrl,
      @Value("${security.oauth2.client.client-id}") String clientId,
      @Value("${security.oauth2.client.client-secret}") String clientSecret
  ) {
    this.webClient = webClientBuilder
        .baseUrl(authBaseUrl)
        .defaultHeaders(headers -> {
          headers.setBasicAuth(clientId, clientSecret);
          headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        })
        .build();
  }

  @Override
  public Mono<AuthenticationDto> getTokenOf(User user) {
    return webClient.post()
        .uri("/oauth/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "password")
            .with("username", user.getUsername())
            .with("password", user.getSocialId()))
        .retrieve()
        .bodyToMono(AuthenticationDto.class);
  }

  @Override
  public Mono<AuthenticationDto> refresh(String refreshToken) {
    return webClient.post()
        .uri("/oauth/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("grant_type", "refresh_token")
            .with("refresh_token", refreshToken))
        .retrieve()
        .bodyToMono(AuthenticationDto.class);
  }
}
