package org.kiworkshop.community.content.mjarticle.api;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.content.mjarticle.api.dto.MjArticleDetailResponseDto;
import org.kiworkshop.community.content.mjarticle.api.dto.MjArticleRequestDto;
import org.kiworkshop.community.content.mjarticle.api.dto.MjArticleResponseDto;
import org.kiworkshop.community.content.mjarticle.service.MjArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/myeongjae/articles")
public class MjArticleController {
  private final MjArticleService mjArticleService;

  @PostMapping
  public Long create(@RequestBody @Valid MjArticleRequestDto mjArticleRequestDto) {
    return mjArticleService.createArticle(mjArticleRequestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    mjArticleService.deleteById(id);
  }

  @GetMapping
  public Page<MjArticleResponseDto> readPage(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    return mjArticleService.readArticlePage(pageable);
  }

  @GetMapping("/{id}")
  public MjArticleDetailResponseDto read(@PathVariable Long id) {
    return mjArticleService.readArticle(id);
  }

  @PutMapping(value = "/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid MjArticleRequestDto mjArticleRequestDto) {
    mjArticleService.updateArticle(id, mjArticleRequestDto);
  }
}
