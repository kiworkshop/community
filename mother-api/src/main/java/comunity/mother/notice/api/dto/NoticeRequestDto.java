package comunity.mother.notice.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class NoticeRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
}
