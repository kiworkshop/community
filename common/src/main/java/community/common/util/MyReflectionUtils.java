package community.common.util;

import java.lang.reflect.Field;
import org.springframework.util.Assert;

/**
 * MyReflectionUtils is only for the testing environment.
 * DO NOT USE UNDER THE PRODUCTION ENVIRONMENT.
 */
public class MyReflectionUtils {
  private MyReflectionUtils() {}

  /**
   * setField inserts the value to the fieldName field of the object.
   * @param object has fieldName field which is set to value.
   * @param fieldName is a name of field of the object to get a value.
   * @param value is inserted to fieldName field of object.
   * @throws NoSuchFieldException when there is no fieldName field in the object.
   * @throws IllegalAccessException when it is impossible to set the value to the fieldName field.
   */
  public static void setField(
      Object object,
      String fieldName,
      Object value
  ) throws NoSuchFieldException, IllegalAccessException {
    Assert.notNull(object, "object to set a value must not be null.");
    Assert.hasText(fieldName, "fieldName must not be blank.");
    Assert.notNull(value, "value must not be null. Be free from NullPointerException!");

    Field field = object.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(object, value);
    field.setAccessible(false);
  }
}
