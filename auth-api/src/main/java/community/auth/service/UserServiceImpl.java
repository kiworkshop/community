package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignUpDto;
import community.auth.api.dto.UserDto;
import community.auth.model.Social;
import community.auth.model.User;
import community.auth.model.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return UserDto.from(
        userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)),
        passwordEncoder
    );
  }

  @Override
  public AuthenticationDto signUp(SignUpDto signUpDto) {
    User user = User.builder()
        .social(new Social()) // TODO: get social info from SocialResourceFetcher.
        .username(UUID.randomUUID().toString())
        .build();

    userRepository.save(user);

    return new AuthenticationDto(); // TODO: get authentication dto from AuthService.
  }
}
