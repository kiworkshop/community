package community.content.myanglog.domain;

public class TagTest {
  public static Tag getTagFixture() {
    return getTagFixture("tagName");
  }

  public static Tag getTagFixture(String tagName) {
    return Tag.builder().name(tagName).build();
  }

}