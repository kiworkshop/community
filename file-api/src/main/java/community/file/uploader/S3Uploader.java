package community.file.uploader;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import community.file.exception.FileNotConvertedException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File uploadFile = convert(multipartFile);
    return upload(uploadFile, dirName);
  }

  private String upload(File uploadFile, String dirName) {
    String fileName = dirName + "/" + uploadFile.getName();
    String uploadImageUrl = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);
    return uploadImageUrl;
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucketName, fileName).toString();
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("{} 파일이 삭제되었습니다.", targetFile.getName());
    } else {
      log.info("{} 파일이 삭제되지 못했습니다.", targetFile.getName());
    }
  }

  private File convert(MultipartFile multipartFile) throws IOException {
    File convertedFile = new File(generateFileName(multipartFile.getOriginalFilename()));
    if (convertedFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
        fos.write(multipartFile.getBytes());
      }
      return convertedFile;
    }

    throw new FileNotConvertedException(multipartFile.getOriginalFilename());
  }

  private String generateFileName(String fileName) {
    int extensionIndex = fileName.lastIndexOf(".");
    String name = fileName.substring(0, extensionIndex);
    String extension = fileName.substring(extensionIndex + 1);
    String eightDigitUuid = UUID.randomUUID().toString().split("-")[0];
    return name + "-" + eightDigitUuid + "." + extension;
  }
}
