package community.content.jgraphy.domain;

import community.common.util.MyReflectionUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class JgraphyTagTest {

  public static JgraphyTag getJgraphyTagFixture() throws Exception{
    return getJgraphyTagFixture(1L);
  }

  private static JgraphyTag getJgraphyTagFixture(long id) throws Exception{
    JgraphyTag jgraphyTag = JgraphyTag.builder().tag("tag").build();
    MyReflectionUtils.setField(jgraphyTag, "id", id);
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