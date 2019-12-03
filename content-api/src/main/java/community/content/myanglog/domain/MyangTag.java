package community.content.myanglog.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class MyangTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "myangTags")
  private Set<MyangPost> myangPosts = new HashSet<>();

  @Builder
  private MyangTag(String name) {
    Assert.hasLength(name, "tag name should not be empty.");
    this.name = name;
  }

  public void addMyangPosts(MyangPost myangPost) {
    myangPosts.add(myangPost);
  }

  public void removeMyangPosts(MyangPost myangPost) {
    myangPosts.remove(myangPost);
  }
}
