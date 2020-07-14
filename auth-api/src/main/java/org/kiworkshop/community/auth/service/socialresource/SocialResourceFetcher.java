package org.kiworkshop.community.auth.service.socialresource;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.kiworkshop.community.auth.api.dto.SocialResourceRequestDto;
import org.kiworkshop.community.auth.api.dto.SocialResourceResponseDto;
import reactor.core.publisher.Mono;

public interface SocialResourceFetcher {
  Mono<SocialResourceResponseDto> fetch(SocialResourceRequestDto socialResourceRequestDto);

  static SocialResourceResponseDto createResourceFrom(JsonNode body, String idFieldName) {
    final String socialId = Optional.ofNullable(body.get(idFieldName))
        .map(JsonNode::asText)
        .filter(StringUtils::isNotEmpty)
        .orElseThrow(() -> new IllegalArgumentException("ResponseBody does not have the field: " + idFieldName));

    final String contactEmail = Optional.ofNullable(body.get("email"))
        .map(JsonNode::asText)
        .orElse("");

    return SocialResourceResponseDto.builder().socialId(socialId).contactEmail(contactEmail).build();
  }
}
