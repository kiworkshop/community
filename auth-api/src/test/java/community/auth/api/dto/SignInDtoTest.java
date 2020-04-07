package community.auth.api.dto;

import community.auth.model.Social;
import org.springframework.test.util.ReflectionTestUtils;

public class SignInDtoTest {
  public static SignInDto getSignInDtoFixture() {
    SignInDto SignInDto = new SignInDto();
    ReflectionTestUtils.setField(SignInDto, "provider", Social.Provider.GOOGLE);
    ReflectionTestUtils.setField(SignInDto, "providerAccessToken", "providerAccessToken");

    return SignInDto;
  }
}
