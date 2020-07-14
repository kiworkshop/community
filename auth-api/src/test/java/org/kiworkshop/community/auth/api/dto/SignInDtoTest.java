package org.kiworkshop.community.auth.api.dto;

import org.kiworkshop.community.auth.model.Social;
import org.springframework.test.util.ReflectionTestUtils;

public class SignInDtoTest {
  public static SignInDto getSignInDtoFixture() {
    SignInDto signInDto = new SignInDto();
    ReflectionTestUtils.setField(signInDto, "provider", Social.Provider.GOOGLE);
    ReflectionTestUtils.setField(signInDto, "providerAccessToken", "providerAccessToken");

    return signInDto;
  }
}
