package org.kiworkshop.community.article.dto;


import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String username;
}
