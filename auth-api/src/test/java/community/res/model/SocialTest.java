package community.res.model;

import static community.res.model.Social.Provider.GOOGLE;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class SocialTest {

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
