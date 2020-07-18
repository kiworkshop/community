package org.kiworkshop.community.auth.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.auth.dto.AuthenticationDto;
import org.kiworkshop.community.auth.dto.SignInDto;
import org.kiworkshop.community.auth.dto.SignUpDto;
import org.kiworkshop.community.auth.dto.TokenRefreshDto;
import org.kiworkshop.community.auth.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/sign-up")
  public Mono<AuthenticationDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
    return userService.signUp(signUpDto);
  }

  @PostMapping("/sign-in")
  public Mono<AuthenticationDto> signIn(@RequestBody @Valid SignInDto signInDto) {
    return userService.signIn(signInDto);
  }

  @PostMapping("/sign-out")
  public Mono<Void> signOut(@RequestBody @Valid TokenRefreshDto tokenRefreshDto) {
    return userService.signOut(tokenRefreshDto);
  }

  @PostMapping("/token-refresh")
  public Mono<AuthenticationDto> tokenRefresh(@RequestBody @Valid TokenRefreshDto tokenRefreshDto) {
    return userService.tokenRefresh(tokenRefreshDto);
  }
}
