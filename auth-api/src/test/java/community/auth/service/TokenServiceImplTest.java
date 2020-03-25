package community.auth.service;

import static community.auth.api.dto.AuthenticationDtoTest.getAuthenticationDtoFixture;
import static community.auth.model.UserTest.getUserFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.api.dto.AuthenticationDto;
import community.auth.model.User;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class TokenServiceImplTest {
  private TokenServiceImpl service;

  private static MockWebServer server;
  private static final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

  @BeforeAll
  static void beforeAll() throws IOException {
    server = new MockWebServer();
    server.start();
  }

  @BeforeEach
  void beforeEach() {
    WebClient.Builder builder = WebClient.builder();
    service = new TokenServiceImpl(
        builder,
        "http://localhost:8082",
        "clientId",
        "clientSecret");

    ReflectionTestUtils.setField(service, "webClient", builder
        .baseUrl(String.format("http://localhost:%s", server.getPort()))
        .build());
  }

  @AfterAll
  static void afterAll() throws IOException {
    server.shutdown();
  }

  @Test
  void getTokenOf_ValidInput_ValidOutput() throws JsonProcessingException {
    // given
    User user = getUserFixture();
    AuthenticationDto expected = getAuthenticationDtoFixture();

    server.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(expected))
        .addHeader("Content-Type", APPLICATION_JSON.toString()));

    // when
    Mono<AuthenticationDto> actual = service.getTokenOf(user);

    // then
    then(actual.block()).isEqualToComparingFieldByField(expected);
  }
}