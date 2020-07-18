package org.kiworkshop.community.user.resource.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.common.domain.BaseEntity;

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
