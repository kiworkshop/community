package community.content.myanglog.domain;

public class MyangTagTest {
  public static MyangTag getTagFixture() {
    return getTagFixture("tagName");
  }

  public static MyangTag getTagFixture(String name) {
    return new MyangTag(name);
  }

}