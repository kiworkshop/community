package org.kiworkshop.community.auth.service.socialresource;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.auth.api.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDtoTest;
import org.kiworkshop.community.auth.model.Social;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class SocialResourceFetcherImplTest {

  private SocialResourceFetcherImpl fetcher;
  private @Mock SocialResourceFetcher googleResourceFetcher;
  private @Mock SocialResourceRequestDto requestDto;

  @BeforeEach
  void setUp() {
    fetcher = new SocialResourceFetcherImpl(googleResourceFetcher);
  }

  @Test
  void fetch_google_ValidOutput() {
    // given
    given(requestDto.getProvider()).willReturn(Social.Provider.GOOGLE.name());

    Mono<SocialResourceResponseDto> expected =
        Mono.just(SocialResourceResponseDtoTest.getSocialResourceResponseDtoFixture());
    given(googleResourceFetcher.fetch(eq(requestDto))).willReturn(expected);

    // when
    Mono<SocialResourceResponseDto> actual = fetcher.fetch(requestDto);

    // then
    then(actual).isEqualTo(expected);
  }
}
