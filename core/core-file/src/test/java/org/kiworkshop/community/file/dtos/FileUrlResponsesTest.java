package org.kiworkshop.community.file.dtos;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class FileUrlResponsesTest {

  @Test
  void modifyUrls_ValidInput_Exception() {
    FileUrlResponses res = new FileUrlResponses(new ArrayList<>());

    thenThrownBy(() -> res.getUrls().add("url")).isInstanceOf(UnsupportedOperationException.class);
  }
}