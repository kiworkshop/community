package org.kiworkshop.community.user.resource.domain.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.kiworkshop.community.auth.model.Social;

public class SocialTest {

  public static Social getSocialFixture() {
    return Social.builder().provider(Social.Provider.GOOGLE).socialId("1").build();
  }

  @Test
  void build_ValidInput_ValidOutput() {
    then(Social.builder().provider(Social.Provider.GOOGLE).socialId("1").build())
        .hasNoNullFieldsOrProperties()
        .hasFieldOrPropertyWithValue("provider", Social.Provider.GOOGLE)
        .hasFieldOrPropertyWithValue("socialId", "1");
  }
}
