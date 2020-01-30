package community.file.api;

import community.file.service.AmazonS3FileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class AmazonS3FileController {

  private final AmazonS3FileService amazonS3FileService;

  @Value("${cloud.aws.s3.bucket.dirName.picture}")
  private String pictureDirName;

  @PostMapping("/upload")
  public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
    return amazonS3FileService.upload(multipartFile, pictureDirName);
  }

}