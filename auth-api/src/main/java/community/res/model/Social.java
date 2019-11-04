package community.res.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Embeddable
@NoArgsConstructor
public class Social {
  public enum Provider {
    GOOGLE, FACEBOOK, TWITTER, NAVER
  }

  @Column(name = "social_provider", length = 20)
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Column(name = "social_id", length = 50)
  private String socialId;

  @Builder
  private Social(
      @NonNull Provider provider,
      @NonNull String socialId
  ) {
    this.provider = provider;
    this.socialId = socialId;
  }
}
