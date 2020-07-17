package org.kiworkshop.community.file.dtos;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class FileUrlResponses {

  private final List<String> urls;

  public FileUrlResponses(List<String> urls) {
    this.urls = Collections.unmodifiableList(urls);
  }
}
