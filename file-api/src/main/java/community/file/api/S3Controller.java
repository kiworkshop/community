package community.file.api;

import community.file.api.dto.FileUrlResponses;
import community.file.uploader.S3Uploader;
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

  @PostMapping("/uploads")
  public FileUrlResponses upload(@RequestParam("data") List<MultipartFile> multipartFiles) {

    return s3Uploader.upload(multipartFiles, PICTURE_DIRECTORY_NAME);
  }
}
