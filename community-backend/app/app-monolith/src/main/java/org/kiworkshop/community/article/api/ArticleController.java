package org.kiworkshop.community.article.api;

import lombok.RequiredArgsConstructor;
import org.kiworkshop.community.article.api.dto.ArticleRequestDto;
import org.kiworkshop.community.article.api.dto.ArticleResponseDto;
import org.kiworkshop.community.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> create(@RequestBody ArticleRequestDto articleRequestDto) {
        return ResponseEntity.ok(articleService.create(articleRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto) {
        articleService.update(id, articleRequestDto);
        return ResponseEntity.ok().build();
    }
}
