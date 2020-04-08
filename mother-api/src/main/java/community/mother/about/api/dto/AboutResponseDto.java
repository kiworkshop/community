package community.mother.about.api.dto;

import community.mother.about.domain.About;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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
