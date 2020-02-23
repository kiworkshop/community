package community.auth.service.socialresource;

import community.auth.api.dto.SocialResourceReqeustDto;
import community.auth.api.dto.SocialResourceResponseDto;
import community.auth.model.Social;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("SocialResourceFetcher")
@RequiredArgsConstructor
public class SocialResourceFetcherImpl implements SocialResourceFetcher {
  private final SocialResourceFetcher googleResourceFetcher;

  @Override
  public Mono<SocialResourceResponseDto> fetch(SocialResourceReqeustDto socialResourceReqeustDto) {
    // TODO: implement social resource fetcher of twitter, facebook, github.
    if (socialResourceReqeustDto.getProvider() != Social.Provider.GOOGLE) {
      throw new IllegalStateException(
          "Only GOOGLE is supported. Input: " + socialResourceReqeustDto.getProvider().toString());
    }

    return googleResourceFetcher.fetch(socialResourceReqeustDto);
  }
}
