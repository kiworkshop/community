package community.content.myanglog.api.dto;

import community.content.myanglog.domain.MyangPost;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyangPostResponseDto {
  private Long id;
  private String title;
  private String content;
  private Set<MyangTagResponseDto> myangTags = new HashSet<>();
  private int likeCount;
  private int viewCount;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  private MyangPostResponseDto(MyangPost myangPost) {
    this.id = myangPost.getId();
    this.title = myangPost.getTitle();
    this.content = myangPost.getContent();
    this.myangTags = myangPost.getMyangTags().stream().map(MyangTagResponseDto::from).collect(Collectors.toSet());
    this.likeCount = myangPost.getLikeCount();
    this.viewCount = myangPost.getViewCount();
    this.createdAt = myangPost.getCreatedAt();
    this.updatedAt = myangPost.getUpdatedAt();
  }

  public static MyangPostResponseDto from(MyangPost myangPost) {
    return new MyangPostResponseDto(myangPost);
  }
}
