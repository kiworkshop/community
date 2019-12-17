package community.auth.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AuthorityTest {

  @ParameterizedTest
  @CsvSource(value = {"1, ROLE_USER", "2, ROLE_ADMIN"})
  void staticObject_ValidInput_ValidOutput(int id, String name) {
    switch (id) {
      case 1:
        then(Authority.USER.getAuthority()).isEqualTo(name);
        break;
      case 2:
        then(Authority.ADMIN.getAuthority()).isEqualTo(name);
        break;
      default:
        throw new RuntimeException("test failed: invalid role id.");
    }
  }
}
