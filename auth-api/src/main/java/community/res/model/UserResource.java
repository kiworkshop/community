package community.res.model;

import community.common.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
class UserResource extends BaseEntity {

  @Column(unique = true)
  private Long userId;
  @Column(unique = true)
  private String nickname;
  private String contactEmail;

  @Builder
  private UserResource(
      Long userId,
      String nickname,
      String contactEmail
  ) {
    this.userId = userId;
    this.nickname = nickname;
    this.contactEmail = contactEmail;
  }
}
