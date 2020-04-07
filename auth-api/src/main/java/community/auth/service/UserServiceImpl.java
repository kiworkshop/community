package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignInDto;
import community.auth.api.dto.SignUpDto;
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
  public Mono<AuthenticationDto> signUp(SignUpDto signUpDto) {
    return socialResourceFetcher.fetch(signUpDto)
        .map(response -> userRepository.save(response.createUser(signUpDto.getProvider())))
        .flatMap(tokenService::getTokenOf);
  }

  @Override
  public Mono<AuthenticationDto> signIn(SignInDto signInDto) {
   return socialResourceFetcher.fetch(signInDto)
        .map(response -> userRepository
            .findBySocialSocialId(response.getSocialId())
            .orElseThrow(() -> UserNotFoundException.fromSocialId(response.getSocialId())))
        .flatMap(tokenService::getTokenOf);
  }
}
