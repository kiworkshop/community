package community.mother.support.api.dto;

import community.mother.support.domain.Support;

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
