package org.kiworkshop.community.auth.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

// @DataJpaTest
@SpringBootTest
@Transactional
class UserRepositoryTest {

  private @Autowired UserRepository userRepository;

  @Test
  void findByUsername_ValidInput_ValidOutput() {
    // given
    User user = UserTest.getUserFixture();
    ReflectionTestUtils.setField(user, "id", null);
    userRepository.save(user);

    then(userRepository.findByUsername(user.getUsername())).isNotEmpty();
  }

  @Test
  void findBySocialSocialId_ValidInput_ValidOutput() {
    // given
    User user = UserTest.getUserFixture();
    ReflectionTestUtils.setField(user, "id", null);
    userRepository.save(user);

    then(userRepository.findBySocialSocialId(user.getSocialId())).isNotEmpty();
  }
}
