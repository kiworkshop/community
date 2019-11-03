package community.auth.model;

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
  private enum Provider {
    GOOGLE, FACEBOOK, TWITTER, NAVER
  }

  @Column(name="social_provider", length = 20)
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Column(name="social_id", length = 50)
  private String id;

  @Builder
  private Social(
      @NonNull Provider provider,
      @NonNull String id
  ) {
    this.provider = provider;
    this.id = id;
  }
}
