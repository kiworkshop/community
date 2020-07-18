package org.kiworkshop.community.auth.service.socialresource;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.kiworkshop.community.auth.dto.SocialResourceResponseDto;

class SocialResourceFetcherTest {

  @Test
  void createResourceFrom_ValidInput_ValidOutput() {
    // given
    final String idFieldName = "id";

    ObjectNode body = JsonNodeFactory.instance.objectNode();
    body.put(idFieldName, "123456789");
    body.put("email", "foo@bar.com");

    // when
    SocialResourceResponseDto socialResourceResponseDto = SocialResourceFetcher.createResourceFrom(body, idFieldName);

    // then
    then(socialResourceResponseDto.getSocialId()).isEqualTo("123456789");
    then(socialResourceResponseDto.getContactEmail()).isEqualTo("foo@bar.com");
  }

  @Test
  void createResourceFrom_emptyField_ThrowException() {
    // given
    final String idFieldName = "id";

    ObjectNode body = JsonNodeFactory.instance.objectNode();
    body.put(idFieldName, "");

    // expect
    thenThrownBy(() -> SocialResourceFetcher.createResourceFrom(body, idFieldName))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ResponseBody does not have the field: " + idFieldName);
  }
}