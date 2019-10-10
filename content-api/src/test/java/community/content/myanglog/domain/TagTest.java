package community.content.myanglog.domain;

public class TagTest {
  public static Tag getTagFixture() {
    return getTagFixture("tagName");
  }

  public static Tag getTagFixture(String name) {
    return Tag.builder().name(name).build();
  }

}