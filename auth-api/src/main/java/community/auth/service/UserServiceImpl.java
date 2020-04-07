package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SocialResourceRequestDto;
import community.auth.api.dto.TokenRefreshDto;
import community.auth.api.dto.UserDto;
import community.auth.exception.UserNotFoundException;
import community.auth.model.UserRepository;
import community.auth.service.socialresource.SocialResourceFetcher;
import lombok.RequiredArgsConstructor;
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
    return UserDto.from(
        userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)),
        passwordEncoder
    );
  }

  @Override
  @Transactional
  public Mono<AuthenticationDto> signUp(SocialResourceRequestDto socialResourceRequestDto) {
    return socialResourceFetcher.fetch(socialResourceRequestDto)
        .map(response -> userRepository.save(response.createUser(socialResourceRequestDto.getProvider())))
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
