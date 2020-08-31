package org.kiworkshop.community.article.api;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.article.api.dto.ArticleRequestDto;
import org.kiworkshop.community.article.api.dto.ArticleResponseDto;
import org.kiworkshop.community.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.read(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        return ResponseEntity.ok(articleService.create(articleRequestDto, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        articleService.delete(id, principal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto, Principal principal) {
        articleService.update(id, articleRequestDto, principal);
        return ResponseEntity.ok().build();
    }
}
