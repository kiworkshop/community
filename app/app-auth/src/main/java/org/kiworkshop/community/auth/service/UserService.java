package org.kiworkshop.community.auth.service;

import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.dto.SignUpDto;
import org.kiworkshop.community.auth.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.dto.TokenRefreshDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  AuthenticationDto signUp(SignUpDto socialResourceRequestDto);

  AuthenticationDto signIn(SocialResourceRequestDto socialResourceRequestDto);

  void signOut(TokenRefreshDto tokenRefreshDto);

  AuthenticationDto tokenRefresh(TokenRefreshDto tokenRefreshDto);
}
