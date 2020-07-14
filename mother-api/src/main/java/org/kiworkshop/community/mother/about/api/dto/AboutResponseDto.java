package org.kiworkshop.community.mother.about.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kiworkshop.community.mother.about.domain.About;

@NoArgsConstructor
@Getter
public class AboutResponseDto {

    private String title;
    private String content;

    private AboutResponseDto(About about) {
        this.title = about.getTitle();
        this.content = about.getContent();
    }

    public static AboutResponseDto from(About about){
        return new AboutResponseDto(about);
    }
}
