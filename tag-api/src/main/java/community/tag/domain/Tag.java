package community.tag.domain;

import community.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Tag(String name) {
        Assert.hasLength(name, "name should not be empty");

        this.name = name;
    }

    public static Tag of(String name) {
        return new Tag(name);
    }
}
