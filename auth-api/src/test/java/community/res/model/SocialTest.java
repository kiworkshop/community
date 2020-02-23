package community.res.model;

import static community.auth.model.Social.Provider.GOOGLE;
import static org.assertj.core.api.BDDAssertions.then;

import community.auth.model.Social;
import org.junit.jupiter.api.Test;

public class SocialTest {

  public static Social getSocialFixture() {
    return Social.builder().provider(GOOGLE).socialId("1").build();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    then(Social.builder().provider(GOOGLE).socialId("1").build())
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("provider", GOOGLE)
        .hasFieldOrPropertyWithValue("socialId", "1");
  }
}
