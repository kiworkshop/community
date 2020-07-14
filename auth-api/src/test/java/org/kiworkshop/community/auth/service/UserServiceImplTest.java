package org.kiworkshop.community.auth.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.kiworkshop.community.auth.model.UserTest.getUserFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.auth.api.dto.AuthenticationDto;
import org.kiworkshop.community.auth.api.dto.AuthenticationDtoTest;
import org.kiworkshop.community.auth.api.dto.SignUpDto;
import org.kiworkshop.community.auth.api.dto.SignUpDtoTest;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDtoTest;
import org.kiworkshop.community.auth.model.User;
import org.kiworkshop.community.auth.model.UserRepository;
import org.kiworkshop.community.auth.service.socialresource.SocialResourceFetcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  private UserServiceImpl userServiceImpl;

  private @Mock UserRepository userRepository;
  private @Mock PasswordEncoder passwordEncoder;
  private @Mock
  SocialResourceFetcher socialResourceFetcher;
  private @Mock TokenService tokenService;

  @BeforeEach
  void setUp() {
    userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder, socialResourceFetcher, tokenService);
  }

  @Test
  void loadUserByUsername_ValidInput_ValidOutput() {
    // given
    User user = getUserFixture();
    given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
    given(passwordEncoder.encode(eq(user.getSocialId()))).willReturn("encrypted socialId");

    // expect
    UserDetails result = userServiceImpl.loadUserByUsername("uuid");

    then(result.getPassword()).isEqualTo("encrypted socialId");
    then(result.getAuthorities()).isEqualTo(user.getAuthorities());
    then(result.getUsername()).isEqualTo(user.getUsername());
    then(result.isAccountNonExpired()).isEqualTo(user.isAccountNonExpired());
    then(result.isAccountNonLocked()).isEqualTo(user.isAccountNonLocked());
    then(result.isCredentialsNonExpired()).isEqualTo(user.isCredentialsNonExpired());
    then(result.isEnabled()).isEqualTo(user.isEnabled());
  }

  @Test
  void loadUserByUsername_NonExistentUsername_UsernameNotFoundException() {
    // given
    given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());

    // expect
    thenThrownBy(() -> userServiceImpl.loadUserByUsername("uuid"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("uuid");
  }

  @Test
  void signUp_ValidInput_ValidOutput() {
    // given
    SignUpDto signUpDto = SignUpDtoTest.getSignUpDtoFixture();
    Mono<SocialResourceResponseDto> socialResourceResponseDtoMono = Mono.just(SocialResourceResponseDtoTest.getSocialResourceResponseDtoFixture());
    User user = getUserFixture();
    Mono<AuthenticationDto> expected = Mono.just(AuthenticationDtoTest.getAuthenticationDtoFixture());

    given(socialResourceFetcher.fetch(eq(signUpDto))).willReturn(socialResourceResponseDtoMono);
    given(userRepository.save(any(User.class))).willReturn(user);
    given(tokenService.getTokenOf(eq(user))).willReturn(expected);

    // when
    Mono<AuthenticationDto> actual = userServiceImpl.signUp(signUpDto);

    // then
    then(actual.block()).isEqualTo(expected.block());
  }
}
