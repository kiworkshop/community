package org.kiworkshop.community.mother.support.api.dto;

import lombok.Getter;
import org.kiworkshop.community.mother.support.domain.Support;

@Getter
public class SupportResponseDto {
    private String title;
    private String content;

    private SupportResponseDto(Support support){
        this.title = support.getTitle();
        this.content = support.getContent();
    }

    public static SupportResponseDto from(Support support){
        return new SupportResponseDto(support);
    }
}
