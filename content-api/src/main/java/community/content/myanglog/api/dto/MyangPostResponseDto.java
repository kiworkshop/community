package community.content.myanglog.api.dto;

import community.content.myanglog.domain.MyangPost;
import community.content.myanglog.domain.Tag;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyangPostResponseDto {
  private Long id;
  private String title;
  private String content;
  private Set<Tag> tags;
  private int likeCount;
  private int viewCount;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public MyangPostResponseDto(MyangPost myangPost) {
    this.id = myangPost.getId();
    this.title = myangPost.getTitle();
    this.content = myangPost.getContent();
    this.tags = myangPost.getTags();
    this.likeCount = myangPost.getLikeCount();
    this.viewCount = myangPost.getViewCount();
    this.createdAt = myangPost.getCreatedAt();
    this.updatedAt = myangPost.getUpdatedAt();
  }

  public static MyangPostResponseDto of(MyangPost myangPost) {
    return new MyangPostResponseDto(myangPost);
  }
}
