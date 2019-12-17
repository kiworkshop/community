package community.content.jgraphy.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JgraphyTagTest {

  public static JgraphyTag getJgraphyTagFixture() throws Exception {
    return getJgraphyTagFixture(1L);
  }

  private static JgraphyTag getJgraphyTagFixture(long id) {
    JgraphyTag jgraphyTag = JgraphyTag.builder().tag("tag").build();
    ReflectionTestUtils.setField(jgraphyTag, "id", id);
    return jgraphyTag;
  }

  @Test
  void build_validInput_validOutput() {
    JgraphyTag jgraphyTag = JgraphyTag.builder()
        .tag("tag")
        .build();

    //then
    then(jgraphyTag)
        .hasNoNullFieldsOrPropertiesExcept("id", "posts")
        .hasFieldOrPropertyWithValue("tag", "tag");
  }

  @Test
  void build_EmptyTitleorContent_ThrowsException() {
    thenThrownBy(() ->
        JgraphyTag.builder()
          .tag("")
          .build()
    ).isInstanceOf(IllegalArgumentException.class);
  }

}