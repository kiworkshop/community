package community.auth.api;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignInDto;
import community.auth.api.dto.SignOutDto;
import community.auth.api.dto.SignUpDto;
import community.auth.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public Mono<Void> signOut(@RequestBody @Valid SignOutDto signOutDto) {
    return userService.signOut(signOutDto);
  }
}
