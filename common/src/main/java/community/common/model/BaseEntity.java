package community.common.model;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Getter
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(columnDefinition = "datetime(6)")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(columnDefinition = "datetime(6)")
  private ZonedDateTime updatedAt;
}
