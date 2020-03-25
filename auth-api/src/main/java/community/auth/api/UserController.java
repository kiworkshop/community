package community.auth.api;

import community.auth.api.dto.AuthenticationDto;
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
}
