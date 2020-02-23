package community.res.model;

import static community.res.model.UserResourceTest.getUserResourceFixture;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

// @DataJpaTest
@SpringBootTest
class UserResourceRepositoryTest {

  private @Autowired UserResourceRepository userResourceRepository;

  @Test
  void findByUsername_ValidInput_ValidOutput() {
    // given
    UserResource userResource = getUserResourceFixture();
    ReflectionTestUtils.setField(userResource, "id", null);
    userResourceRepository.save(userResource);

    then(userResourceRepository.findByUsername(userResource.getUsername())).isNotEmpty();
  }
}
