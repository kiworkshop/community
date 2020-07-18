package org.kiworkshop.community.auth.service;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.dto.TokenRefreshDto;
import org.kiworkshop.community.auth.exception.UserNotFoundException;
import org.kiworkshop.community.auth.model.UserRepository;
import org.kiworkshop.community.auth.service.socialresource.SocialResourceFetcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
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
  public Mono<AuthenticationDto> signUp(SocialResourceRequestDto socialResourceRequestDto) {
    return socialResourceFetcher.fetch(socialResourceRequestDto)
        .map(response -> userRepository.save(
            UserConverter.toEntity(response, socialResourceRequestDto.getProvider())))
        .flatMap(tokenService::getTokenOf);
  }

  @Override
  public Mono<AuthenticationDto> signIn(SocialResourceRequestDto socialResourceRequestDto) {
    return socialResourceFetcher.fetch(socialResourceRequestDto)
        .map(response -> userRepository
            .findBySocialSocialId(response.getSocialId())
            .orElseThrow(() -> UserNotFoundException.fromSocialId(response.getSocialId())))
        .flatMap(tokenService::getTokenOf);
  }

  @Override
  public Mono<Void> signOut(TokenRefreshDto tokenRefreshDto) {
    return tokenService.refresh(tokenRefreshDto.getRefreshToken()).then();
  }

  @Override
  public Mono<AuthenticationDto> tokenRefresh(TokenRefreshDto tokenRefreshDto) {
    return tokenService.refresh(tokenRefreshDto.getRefreshToken());
  }
}
