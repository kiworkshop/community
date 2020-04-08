package community.tag.domain;

//import community.table.domain.ContentTable;
import org.junit.jupiter.api.Test;

import static community.tag.domain.TagTest.getTagFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class ContentTableTest {

    @Test
    void build_ValidInput_ValidOutput() {
        // given
        Tag tag = getTagFixture();
        String contentType = "simplelife";
        Long contentId = 1L;

        // when
        TagContent tagContent = TagContent.builder()
                .tag(tag)
                .contentType(contentType)
                .contentId(contentId).build();

        // then
        then(tagContent).hasNoNullFieldsOrPropertiesExcept("id", "createdAt", "updatedAt")
                .hasFieldOrPropertyWithValue("tag", tag)
                .hasFieldOrPropertyWithValue("contentType", contentType)
                .hasFieldOrPropertyWithValue("contentId", contentId);
    }

   @Test
   void build_EmptyContentType_ThrowException() {
       // given
       Tag tag = getTagFixture();

       // then
       thenThrownBy(() ->
               TagContent.builder()
                .tag(tag)
                .contentType("")
                .contentId(1L).build()
       ).isInstanceOf(IllegalArgumentException.class);
   }

}