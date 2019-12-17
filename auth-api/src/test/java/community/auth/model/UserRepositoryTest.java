package community.auth.model;

import static community.auth.model.UserTest.getUserFixture;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

// @DataJpaTest
@SpringBootTest
class UserRepositoryTest {

  private @Autowired UserRepository userRepository;

  @Test
  void findByUsername_ValidInput_ValidOutput() {
    // given
    User user = getUserFixture();
    ReflectionTestUtils.setField(user, "id", null);
    userRepository.save(user);

    then(userRepository.findByUsername(user.getUsername())).isNotEmpty();
  }
}
