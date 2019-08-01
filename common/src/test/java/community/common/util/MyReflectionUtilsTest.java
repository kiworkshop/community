package community.common.util;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;

class MyReflectionUtilsTest {
  private class ClassToTestReflectionUtils {
    private String someString = null;

    String getSomeString() {
      return someString;
    }
  }

  @Test
  void setField_ValidInput_ValidOutput() throws NoSuchFieldException, IllegalAccessException {
    // given
    ClassToTestReflectionUtils obj = new ClassToTestReflectionUtils();
    then(obj.getSomeString()).isNull();

    // when
    MyReflectionUtils.setField(obj, "someString", "some value");

    // then
    then(obj.getSomeString()).isEqualTo("some value");
  }

  @Test
  void setField_NullObject_ThrowException() {
    // expect
    thenThrownBy(() -> MyReflectionUtils.setField(null, "someString", "some value"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("object to set a value must not be null.");
  }

  @Test
  void setField_NullFieldName_ThrowException() {
    // given
    ClassToTestReflectionUtils obj = new ClassToTestReflectionUtils();

    // expect
    thenThrownBy(() -> MyReflectionUtils.setField(obj, null, "some value"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("fieldName must not be blank.");
  }

  @Test
  void setField_BlankFieldName_ThrowException() {
    // given
    ClassToTestReflectionUtils obj = new ClassToTestReflectionUtils();

    // expect
    thenThrownBy(() -> MyReflectionUtils.setField(obj, "  \t \n", "some value"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("fieldName must not be blank.");
  }

  @Test
  void setField_NullValue_ThrowException() {
    // given
    ClassToTestReflectionUtils obj = new ClassToTestReflectionUtils();

    // expect
    thenThrownBy(() -> MyReflectionUtils.setField(obj, "someString", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("value must not be null. Be free from NullPointerException!");
  }
}
