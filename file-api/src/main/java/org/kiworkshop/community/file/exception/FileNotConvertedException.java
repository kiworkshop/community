package org.kiworkshop.community.file.exception;

public class FileNotConvertedException extends IllegalArgumentException {

  public FileNotConvertedException(String fileName) {
    super(fileName + " 파일의 multipartFile -> File로의 변환에 실패했습니다.");
  }

}
