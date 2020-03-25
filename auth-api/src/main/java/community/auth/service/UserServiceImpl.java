package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignUpDto;
import community.auth.api.dto.UserDto;
import community.auth.model.Social;
import community.auth.model.User;
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
    return socialResourceFetcher.fetch(signUpDto).map(response -> {
      User user = User.builder()
          .social(Social.builder()
              .provider(signUpDto.getProvider())
              .socialId(response.getSocialId())
              .build())
          .build();

      return userRepository.save(user);
    }).flatMap(user -> tokenService.getToken(user.getUsername(), user.getSocialId()));
  }
}
