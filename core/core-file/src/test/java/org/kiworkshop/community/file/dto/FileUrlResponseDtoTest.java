package org.kiworkshop.community.file.dto;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class FileUrlResponseDtoTest {

  @Test
  void modifyUrls_ValidInput_Exception() {
    FileUrlResponseDto res = new FileUrlResponseDto(new ArrayList<>());

    thenThrownBy(() -> res.getUrls().add("url")).isInstanceOf(UnsupportedOperationException.class);
  }
}