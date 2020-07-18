package org.kiworkshop.community.auth.api.dto;

import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.springframework.test.util.ReflectionTestUtils;

public class AuthenticationDtoTest {
  public static AuthenticationDto getAuthenticationDtoFixture() {
    AuthenticationDto authenticationDto = new AuthenticationDto();

    ReflectionTestUtils.setField(authenticationDto, "accessToken", "accessToken");
    ReflectionTestUtils.setField(authenticationDto, "tokenType", "tokenType");
    ReflectionTestUtils.setField(authenticationDto, "refreshToken", "refreshToken");
    ReflectionTestUtils.setField(authenticationDto, "expiresIn", 3600);
    ReflectionTestUtils.setField(authenticationDto, "scope", "read write");

    return authenticationDto;
  }
}
