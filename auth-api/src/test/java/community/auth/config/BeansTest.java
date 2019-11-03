package community.auth.config;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class BeansTest {
  @Test
  void passwordEncoderEncode_ValidInput_ValidOutput() {
    // given
    String password = "password";
    PasswordEncoder passwordEncoder = new Beans().passwordEncoder();

    // when
    String encoded = passwordEncoder.encode(password);
    System.out.println(encoded);

    // then
    then(passwordEncoder.matches(password, encoded)).isTrue();
  }
}
