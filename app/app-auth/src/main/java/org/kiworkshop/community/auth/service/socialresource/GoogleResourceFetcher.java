package org.kiworkshop.community.auth.service.socialresource;

import com.fasterxml.jackson.databind.JsonNode;
import org.kiworkshop.community.auth.api.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDto;
import org.kiworkshop.community.auth.model.Social;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
class GoogleResourceFetcher implements SocialResourceFetcher {
  private WebClient webClient;
  private static final String BASE_URL = "https://oauth2.googleapis.com/tokeninfo";

  public GoogleResourceFetcher(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
  }

  @Override
  public Mono<SocialResourceResponseDto> fetch(SocialResourceRequestDto socialResourceRequestDto) {
    Assert.isTrue(
        Social.Provider.valueOf(socialResourceRequestDto.getProvider()) == Social.Provider.GOOGLE,
        "Provider must be GOOGLE. Current: " + socialResourceRequestDto.getProvider()
    );

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("access_token", socialResourceRequestDto.getProviderAccessToken())
            .build()
        )
        .retrieve()
        .bodyToMono(JsonNode.class)
        .map(body -> SocialResourceFetcher.createResourceFrom(body, "sub"));
  }
}
