package community.auth.service.socialresource;

import community.auth.api.dto.SocialResourceRequestDto;
import community.auth.api.dto.SocialResourceResponseDto;
import community.auth.model.Social;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Primary
public class SocialResourceFetcherImpl implements SocialResourceFetcher {
  private final SocialResourceFetcher googleResourceFetcher;

  @Override
  public Mono<SocialResourceResponseDto> fetch(SocialResourceRequestDto socialResourceRequestDto) {
    // TODO: implement social resource fetcher of twitter, facebook, github.
    if (socialResourceRequestDto.getProvider() != Social.Provider.GOOGLE) {
      throw new IllegalStateException(
          "Only GOOGLE is supported. Input: " + socialResourceRequestDto.getProvider().toString());
    }

    return googleResourceFetcher.fetch(socialResourceRequestDto);
  }
}
