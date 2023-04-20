package ru.liga.prerevolutionary.tinderserver.image;

import java.lang.reflect.Field;

public class ReflectionHelper {

    /**
     * Получение значения поля
     *
     * @param object объект, значение поля которого хотим получить
     * @param name   название поля
     * @return значение поля
     */
    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }
}
