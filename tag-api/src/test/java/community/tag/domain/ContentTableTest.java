package community.tag.domain;

import community.table.domain.ContentTable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTableTest {
    @Test
    void build_ValidInput_ValidOutput() {
        // given
        String name = "tag";
        Tag tag = Tag.of(name);
        String tableName = "simpelife";
        Long contentId = 1L;
        // when
        ContentTable contentTable = ContentTable.builder()
                .tag(tag)
                .tableName(tableName)
                .contentId(contentId).build();
        // then
        assertThat(contentTable.getTag()).isEqualTo(tag);
        assertThat(contentTable.getTableName()).isEqualTo(tableName);
        assertThat(contentTable.getContentId()).isEqualTo(contentId);
    }
}