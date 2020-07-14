package community.file.api.dto;

import java.util.List;

public class FileUrlResponses {

  private List<String> urls;

  public FileUrlResponses(List<String> urls) {
    this.urls = urls;
  }

  public List<String> getUrls() {
    return urls;
  }
}
