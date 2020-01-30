package community.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmazonS3FileService {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File fileToUpload = convertToFileFrom(multipartFile);
    return upload(fileToUpload, dirName);
  }

  private String upload(File fileToUpload, String dirName) {
    String fileName = dirName + "/" + fileToUpload.getName();
    String uploadedFileUrl = addToS3(fileToUpload, fileName);
    removeConvertedFile(fileToUpload);
    return uploadedFileUrl;
  }

  private String addToS3(File fileToUpload, String fileName) {
    amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, fileToUpload).withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucketName, fileName).toString();
  }

  private void removeConvertedFile(File fileToRemove) {
    if (fileToRemove.delete()) {
      log.info("파일이 삭제 되었습니다.");
    } else {
      log.info("파일이 삭제 되지 못했습니다.");
    }
  }

  private File convertToFileFrom(MultipartFile multipartFile) throws IOException{
    File convertedFile = new File(multipartFile.getOriginalFilename());
    multipartFile.transferTo(convertedFile);
    return convertedFile;
  }

}