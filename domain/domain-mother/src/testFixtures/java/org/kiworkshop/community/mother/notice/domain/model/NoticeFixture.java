package org.kiworkshop.community.mother.notice.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeFixture {
  public static Notice get() {
    return get(1L);
  }

  public static Notice get(long id) {
    Notice notice = Notice.builder().title("title").content("content").build();
    ReflectionTestUtils.setField(notice, "id", id);

    return notice;
  }
}
