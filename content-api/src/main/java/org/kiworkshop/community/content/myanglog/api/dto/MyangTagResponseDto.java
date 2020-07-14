package org.kiworkshop.community.content.myanglog.api.dto;

import lombok.Getter;
import org.kiworkshop.community.content.myanglog.domain.MyangTag;

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
