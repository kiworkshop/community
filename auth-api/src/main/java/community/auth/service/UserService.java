package community.auth.service;

import community.auth.api.dto.AuthenticationDto;
import community.auth.api.dto.SignUpDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  AuthenticationDto signUp(SignUpDto signUpDto);
}
