package org.kiworkshop.community.article.api;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.article.dto.ArticleRequestDto;
import org.kiworkshop.community.article.dto.ArticleResponseDto;
import org.kiworkshop.community.article.domain.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public Long create(@RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        return articleService.create(articleRequestDto);
    }

    @GetMapping
    public Page<ArticleResponseDto> readPage(
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return articleService.readPage(pageable);
    }

    @GetMapping("/{id}")
    public ArticleResponseDto read(@PathVariable Long id) {
        return articleService.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        articleService.delete(id, principal);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        articleService.update(id, articleRequestDto);
    }
}
