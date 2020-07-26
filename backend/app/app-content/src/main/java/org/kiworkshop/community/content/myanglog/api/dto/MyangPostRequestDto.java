package org.kiworkshop.community.content.myanglog.api.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.kiworkshop.community.content.myanglog.domain.MyangTag;

@Getter
public class MyangPostRequestDto {
  private @NotEmpty String title;
  private @NotEmpty String content;
  private @NotNull Set<MyangTag> myangTags = new HashSet<>();
}
