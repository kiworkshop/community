package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignInDto;
import community.auth.api.dto.SignUpDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {
  Mono<AuthenticationDto> signUp(SignUpDto signUpDto);

  Mono<AuthenticationDto> signIn(SignInDto signInDto);
}
