package community.auth.api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.AuthAndResApiApplication;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = AuthAndResApiApplication.class, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerIntTest {

  private @Autowired MockMvc mvc;
  private @Autowired ObjectMapper objectMapper;

  private String authorization;

  @Value("${security.oauth2.client.client-id}")
  private String clientId;
  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;

  @BeforeEach
  void setUp() {
    String origin = clientId + ":" + clientSecret;

    authorization = "Basic " + Base64.getEncoder().encodeToString(origin.getBytes());
  }

  @Test
  void generateToken_ValidInput_ValidOutput() throws Exception {
    this.mvc.perform(post("/oauth/token")
        .header("Authorization", authorization)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("grant_type", "password")
        .param("username", "123e4567-e89b-12d3-a456-426655440000")
        .param("password", "password"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token").isString())
        .andExpect(jsonPath("$.token_type").isString())
        .andExpect(jsonPath("$.expires_in").isNumber())
        .andExpect(jsonPath("$.refresh_token").isString())
        .andExpect(jsonPath("$.scope").isString());
  }

  @Test
  void checkToken_ValidInput_ValidOutput() throws Exception {
    String responseBody = this.mvc.perform(post("/oauth/token")
        .header("Authorization", authorization)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("grant_type", "password")
        .param("username", "123e4567-e89b-12d3-a456-426655440000")
        .param("password", "password"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    String accessToken = objectMapper.readTree(responseBody).findValue("access_token").asText();

    this.mvc.perform(post("/oauth/check_token")
        .header("Authorization", authorization)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("token", accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.exp").isNumber())
        .andExpect(jsonPath("$.user_name").isString())
        .andExpect(jsonPath("$.authorities").isArray())
        .andExpect(jsonPath("$.authorities.length()").value(2))
        .andExpect(jsonPath("$.scope").isArray());
  }

  @Test
  void refreshToken_ValidInput_ValidOutput() throws Exception {
    String responseBody = this.mvc.perform(post("/oauth/token")
        .header("Authorization", authorization)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("grant_type", "password")
        .param("username", "123e4567-e89b-12d3-a456-426655440000")
        .param("password", "password"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    String refreshToken = objectMapper.readTree(responseBody).findValue("refresh_token").asText();

    this.mvc.perform(post("/oauth/token")
        .header("Authorization", authorization)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("grant_type", "refresh_token")
        .param("refresh_token", refreshToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token").isString())
        .andExpect(jsonPath("$.token_type").isString())
        .andExpect(jsonPath("$.expires_in").isNumber())
        .andExpect(jsonPath("$.refresh_token").isString())
        .andExpect(jsonPath("$.scope").isString());
  }
}
