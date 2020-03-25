package community.tag.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class TagContentRequestDto {
    private @NotEmpty List<String> tagNames;
    private @NotNull String tableName;
    private @NotNull Long contentId;
}
