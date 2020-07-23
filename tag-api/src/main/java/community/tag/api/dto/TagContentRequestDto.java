package community.tag.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class TagContentRequestDto {
    private @NotEmpty List<@NotEmpty String> tagNames;
    private @NotEmpty String contentType;
    private @NotNull Long contentId;
}
