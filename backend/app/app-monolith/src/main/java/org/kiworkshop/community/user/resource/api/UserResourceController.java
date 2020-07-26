package org.kiworkshop.community.user.resource.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.user.resource.domain.service.UserResourceService;
import org.kiworkshop.community.user.resource.dto.UserResourceResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-resources")
public class UserResourceController {
  private final UserResourceService userResourceService;

  @GetMapping("/me")
  public UserResourceResponseDto me(Principal principal) {
    return userResourceService.getUserResource(principal.getName());
  }
}
