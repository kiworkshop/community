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
    public ResponseEntity<Long> create(@RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        return ResponseEntity.ok(articleService.create(articleRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDto>> readPage(
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(articleService.readPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        articleService.delete(id, principal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        articleService.update(id, articleRequestDto);
        return ResponseEntity.ok().build();
    }
}
