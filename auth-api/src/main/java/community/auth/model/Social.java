package community.auth.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Social implements Serializable {

  public static final Social empty = Social.builder().provider(Provider.NONE).socialId("").build();

  public enum Provider {
    NONE, GOOGLE, FACEBOOK, TWITTER, NAVER
  }

  @Column(name = "social_provider", length = 20)
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Column(name = "social_id", length = 256, unique = true)
  private String socialId;

  @Builder
  private Social(Provider provider, String socialId) {
    this.provider = provider;
    this.socialId = socialId;
  }
}
