package community.tag.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TagRequestDto {
    private @NotNull String name;
}
