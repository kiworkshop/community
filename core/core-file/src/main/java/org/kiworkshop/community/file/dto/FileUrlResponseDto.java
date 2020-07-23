package org.kiworkshop.community.file.dto;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class FileUrlResponseDto {

  private final List<String> urls;

  public FileUrlResponseDto(List<String> urls) {
    this.urls = Collections.unmodifiableList(urls);
  }
}
