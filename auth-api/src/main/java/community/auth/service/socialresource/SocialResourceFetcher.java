package community.auth.service.socialresource;

import com.fasterxml.jackson.databind.JsonNode;
import community.auth.api.dto.SocialResourceReqeustDto;
import community.auth.api.dto.SocialResourceResponseDto;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

public interface SocialResourceFetcher {
  Mono<SocialResourceResponseDto> fetch(SocialResourceReqeustDto socialResourceReqeustDto);

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
