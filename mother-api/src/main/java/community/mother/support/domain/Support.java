package community.mother.support.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Support {
    private String title;
    private String content;

    @Builder
    private Support(String title, String content){
        Assert.hasLength(title, "title should not be empty.");
        Assert.hasLength(content, "content should not be empty");

        this.title = title;
        this.content = content;
    }
}
