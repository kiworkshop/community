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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class MyangTag {

  public MyangTag(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "myangTags")
  private Set<MyangPost> myangPosts = new HashSet<>();

  public void addMyangPosts(MyangPost myangPost) {
    myangPosts.add(myangPost);
  }

  public void removeMyangPosts(MyangPost myangPost) {
    myangPosts.remove(myangPost);
  }
}
