package community.auth.service.socialresource;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import community.auth.api.dto.SocialResourceReqeustDto;
import community.auth.api.dto.SocialResourceResponseDto;
import community.auth.model.Social;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class GoogleResourceFetcherTest {

  private GoogleResourceFetcher fetcher;

  private static MockWebServer server;
  private @Mock SocialResourceReqeustDto requestDto;

  @BeforeAll
  static void init() throws IOException {
    server = new MockWebServer();
    server.start();
  }

  @AfterAll
  static void tearDownAll() throws IOException {
    server.shutdown();
  }

  @BeforeEach
  void setUp() {
    WebClient.Builder builder = WebClient.builder();
    fetcher = new GoogleResourceFetcher(builder);

    ReflectionTestUtils.setField(fetcher, "webClient", builder
        .baseUrl(String.format("http://localhost:%s", server.getPort()))
        .build());
  }

  @Test
  void fetch_ValidInput_ValidOutput() {
    server.enqueue(new MockResponse()
        .setBody("{\n" +
            "  \"sub\": \"socialId\",\n" +
            "  \"email\": \"foo@bar.com\"\n" +
            "}")
        .addHeader("Content-Type", APPLICATION_JSON.toString()));

    given(requestDto.getProvider()).willReturn(Social.Provider.GOOGLE);
    given(requestDto.getProviderAccessToken()).willReturn("providerAccessToken");

    SocialResourceResponseDto response = fetcher.fetch(requestDto).block();
    assert response != null;

    then(response.getSocialId()).isEqualTo("socialId");
    then(response.getContactEmail()).isEqualTo("foo@bar.com");
  }
}
