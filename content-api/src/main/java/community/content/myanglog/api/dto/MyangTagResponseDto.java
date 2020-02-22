package community.content.myanglog.api.dto;

import community.content.myanglog.domain.MyangTag;
import lombok.Getter;

@Getter
public class MyangTagResponseDto {
  private Long id;
  private String name;

  private MyangTagResponseDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static MyangTagResponseDto from(MyangTag tag) {
    return new MyangTagResponseDto(tag.getId(), tag.getName());
  }
}
