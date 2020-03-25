package community.auth.api.dto;

import static org.assertj.core.api.BDDAssertions.then;

import community.auth.model.Social;
import community.auth.model.User;
import org.junit.jupiter.api.Test;

public class SocialResourceResponseDtoTest {
  public static SocialResourceResponseDto getSocialResourceResponseDtoFixture() {
    return SocialResourceResponseDto.builder()
        .socialId("socialId")
        .contactEmail("foo@bar.com")
        .build();
  }

  @Test
  void createUser_ValidInput_ValidOutput() {
    // given
    SocialResourceResponseDto response = getSocialResourceResponseDtoFixture();

    // when
    User user = response.createUser(Social.Provider.GOOGLE);

    // then
    then(user.getSocialId()).isEqualTo(response.getSocialId());
    then(user.getSocial().getProvider()).isEqualTo(Social.Provider.GOOGLE);
  }
}
