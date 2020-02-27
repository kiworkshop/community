package community.file.controller;

import community.file.uploader.S3Uploader;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {

  private static final String PICTURE_DIRECTORY_NAME = "picture";

  private final S3Uploader s3Uploader;

  @PostMapping("/upload")
  public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
    return s3Uploader.upload(multipartFile, PICTURE_DIRECTORY_NAME);
  }

  @PostMapping("/uploads")
  public List<String> upload(@RequestParam("data") List<MultipartFile> multipartFiles) throws IOException {
    return s3Uploader.upload(multipartFiles, PICTURE_DIRECTORY_NAME);
  }

}
