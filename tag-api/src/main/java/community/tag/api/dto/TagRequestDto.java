package community.tag.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class TagRequestDto {
    private @NotEmpty String name;
}
