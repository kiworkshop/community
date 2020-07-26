package org.kiworkshop.community.auth.api.dto;

import org.kiworkshop.community.auth.dto.SignUpDto;
import org.kiworkshop.community.auth.model.Social;
import org.springframework.test.util.ReflectionTestUtils;

public class SignUpDtoTest {
  public static SignUpDto getSignUpDtoFixture() {
    SignUpDto signUpDto = new SignUpDto();
    ReflectionTestUtils.setField(signUpDto, "provider", Social.Provider.GOOGLE.name());
    ReflectionTestUtils.setField(signUpDto, "providerAccessToken", "providerAccessToken");
    ReflectionTestUtils.setField(signUpDto, "nickname", "nickname");

    return signUpDto;
  }
}
