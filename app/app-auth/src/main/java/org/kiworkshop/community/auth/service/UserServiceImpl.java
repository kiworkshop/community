package org.kiworkshop.community.auth.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.dto.SignUpDto;
import org.kiworkshop.community.auth.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.dto.TokenRefreshDto;
import org.kiworkshop.community.auth.exception.UserNotFoundException;
import org.kiworkshop.community.auth.model.UserRepository;
import org.kiworkshop.community.auth.service.socialresource.SocialResourceFetcher;
import org.kiworkshop.community.user.resource.domain.model.UserResourceRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserResourceRepository userResourceRepository;
  private final PasswordEncoder passwordEncoder;
  private final SocialResourceFetcher socialResourceFetcher;
  private final TokenService tokenService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return UserConverter.toDto(
        userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)),
        passwordEncoder
    );
  }

  @Override
  @Transactional
  public AuthenticationDto signUp(SignUpDto signUpDto) {
    var response = Objects.requireNonNull(socialResourceFetcher.fetch(signUpDto).block());

    var user = userRepository.save(UserConverter.toEntity(response, signUpDto.getProvider()));
    userResourceRepository.save(UserConverter.toUserResource(user, signUpDto, response));

    return tokenService.getTokenOf(user).block();
  }

  @Override
  public AuthenticationDto signIn(SocialResourceRequestDto socialResourceRequestDto) {
    var response = Objects.requireNonNull(socialResourceFetcher.fetch(socialResourceRequestDto).block());

    var user = userRepository
        .findBySocialSocialId(response.getSocialId())
        .orElseThrow(() -> UserNotFoundException.fromSocialId(response.getSocialId()));

    return tokenService.getTokenOf(user).block();
  }

  @Override
  public void signOut(TokenRefreshDto tokenRefreshDto) {
    tokenService.refresh(tokenRefreshDto.getRefreshToken()).block();
  }

  @Override
  public AuthenticationDto tokenRefresh(TokenRefreshDto tokenRefreshDto) {
    return tokenService.refresh(tokenRefreshDto.getRefreshToken()).block();
  }
}
