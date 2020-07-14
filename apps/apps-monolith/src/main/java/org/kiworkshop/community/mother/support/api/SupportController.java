package org.kiworkshop.community.mother.support.api;


import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.mother.dtos.SupportResponseDto;
import org.kiworkshop.community.mother.support.service.SupportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/support")
public class SupportController {
  private final SupportService supportService;

  @GetMapping
  public SupportResponseDto readSupport() {
    return supportService.readSupport();
  }
}
