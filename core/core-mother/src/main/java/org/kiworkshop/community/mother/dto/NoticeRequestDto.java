package org.kiworkshop.community.mother.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class NoticeRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
