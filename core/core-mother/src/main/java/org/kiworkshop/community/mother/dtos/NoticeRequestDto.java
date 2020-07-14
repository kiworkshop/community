package org.kiworkshop.community.mother.dtos;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class NoticeRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
