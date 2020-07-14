package org.kiworkshop.community.mother.notice.domain.model;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(columnDefinition = "datetime(6)")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(columnDefinition = "datetime(6)")
  private ZonedDateTime updatedAt;

  private String title;

  @Column(columnDefinition = "text")
  private String content;

  @Builder
  private Notice(String title, String content) {
    Assert.hasLength(title, "title must have length.");
    Assert.notNull(content, "content must not be null");

    this.title = title;
    this.content = content;
  }

  public void updateNotice(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
