package community.content.jgraphy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
@Entity
public class JgraphyTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tag;

  @Builder
  private JgraphyTag(
      String tag
  ) {
    Assert.hasLength(tag, "tag should be needed");
    this.tag = tag;
  }

}
