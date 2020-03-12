package community.tag.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class TagContentRequestDto {
    private @NotEmpty List<String> tagNames;
    private @NotEmpty String tableName;
    private @NotEmpty Long contentId;
}
