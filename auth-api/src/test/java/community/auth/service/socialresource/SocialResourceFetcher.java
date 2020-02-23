package community.auth.service.socialresource;

import community.auth.api.dto.SocialResourceReqeustDto;
import community.auth.api.dto.SocialResourceResponseDto;

public interface SocialResourceFetcher {
  SocialResourceResponseDto fetch(SocialResourceReqeustDto socialResourceReqeustDto);
}
