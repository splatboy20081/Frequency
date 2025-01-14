package xyz.elevated.frequency.util;

import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtil {

  /**
   * @param object - The object you want to grab and modify the declared field from
   * @param fieldName - The name of the declared field you would like to modify.
   * @param alteration - The new value you want to give to the field.
   */
  public void modifyDeclaredField(Object object, String fieldName, Object alteration) {
    try {
      Field declaredField = object.getClass().getDeclaredField(fieldName);

      declaredField.setAccessible(true);
      declaredField.set(object, alteration);

      declaredField.setAccessible(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param object - The object you want to modify the field from.
   * @param fieldName - The name of the field you would like to modify.
   * @param alteration - The new value you want to give to the field.
   */
  public void modifyField(Object object, String fieldName, Object alteration) {
    try {
      Field field = object.getClass().getField(fieldName);

      field.setAccessible(true);
      field.set(object, alteration);

      field.setAccessible(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param clazz - The class you want to grab a value from.
   * @param fieldName - The name of the field you want to get the value from.
   * @param type - The type of the value of the field.
   * @param instance - The instance for the class
   * @return - The value if found.
   */
  public <T> T getFieldValue(Class<?> clazz, String fieldName, Class<?> type, Object instance) {
    Field field = getField(clazz, fieldName, type);

    field.setAccessible(true);

    try {
      //noinspection unchecked
      return (T) field.get(instance);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Failed to get value of field '" + field.getName() + "'");
    }
  }

  /**
   * @param clazz - The class you want to grab the value from.
   * @param name - The name of the field you want to grab.
   * @param type - The type of data the field has.
   */
  private Field getField(Class<?> clazz, String name, Class<?> type) {
    try {
      Field field = clazz.getDeclaredField(name);

      field.setAccessible(true);

      if (field.getType() != type) {
        throw new IllegalStateException(
            "Invalid action for field '"
                + name
                + "' (expected "
                + type.getName()
                + ", got "
                + field.getType().getName()
                + ")");
      }
      return field;
    } catch (Exception e) {
      throw new RuntimeException("Failed to get field '" + name + "'");
    }
  }
}
