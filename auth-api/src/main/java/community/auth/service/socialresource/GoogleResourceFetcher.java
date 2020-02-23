package community.auth.service.socialresource;

import com.fasterxml.jackson.databind.JsonNode;
import community.auth.api.dto.SocialResourceReqeustDto;
import community.auth.api.dto.SocialResourceResponseDto;
import community.auth.model.Social;
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
  public Mono<SocialResourceResponseDto> fetch(SocialResourceReqeustDto socialResourceReqeustDto) {
    Assert.isTrue(
        socialResourceReqeustDto.getProvider() == Social.Provider.GOOGLE,
        "Provider must be GOOGLE. Current: " + socialResourceReqeustDto.getProvider().name()
    );

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/tokeninfo")
            .queryParam("access_token", socialResourceReqeustDto.getProviderAccessToken())
            .build()
        )
        .retrieve()
        .bodyToMono(JsonNode.class)
        .map(body -> SocialResourceFetcher.createResourceFrom(body , "sub"));
  }
}
