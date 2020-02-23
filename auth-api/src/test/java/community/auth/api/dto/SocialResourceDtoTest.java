package community.auth.api.dto;

import lombok.val;
import org.springframework.test.util.ReflectionTestUtils;

public class SocialResourceDtoTest {
  public static SocialResourceResponseDto getSocialResourceFixture() {
    val dto = new SocialResourceResponseDto();
    ReflectionTestUtils.setField(dto, "socialId", "socialId");
    ReflectionTestUtils.setField(dto, "contactEmail", "foo@bar.com");

    return dto;
  }
}
