package community.content.myanglog.domain;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
@ToString(exclude = "myangPosts")
public class MyangTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @ManyToMany(mappedBy = "myangTags")
  private Set<MyangPost> myangPosts;

  @Builder
  private MyangTag(String name) {
    Assert.hasLength(name, "tag name should not be empty.");
    this.name = name;
  }

  public void addNewMyangPosts(MyangPost myangPost) {
    this.myangPosts = Stream.of(myangPost).collect(Collectors.toSet());
  }
}
