package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignUpDto;
import community.auth.api.dto.SocialResourceResponseDto;
import community.auth.api.dto.UserDto;
import community.auth.model.Social;
import community.auth.model.User;
import community.auth.model.UserRepository;
import community.auth.service.socialresource.SocialResourceFetcher;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SocialResourceFetcher socialResourceFetcher;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return UserDto.from(
        userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)),
        passwordEncoder
    );
  }

  @Override
  @Transactional
  public AuthenticationDto signUp(SignUpDto signUpDto) {
    SocialResourceResponseDto response = Optional.ofNullable(socialResourceFetcher.fetch(signUpDto).block())
        .orElseThrow(() -> new IllegalStateException(
            "No response body from social Provider: " + signUpDto.getProvider().name()));

    User user = User.builder()
        .social(Social.builder()
            .provider(signUpDto.getProvider())
            .socialId(response.getSocialId())
            .build())
        .build();

    userRepository.save(user);

    return new AuthenticationDto(); // TODO: get authentication dto from AuthService.
  }
}
