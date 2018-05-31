package org.codnect.firesnap.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class ReflectionUtil {

    /**
     * Determines if the field is a property.
     *
     * @param field the field object
     * @return if the field is a property, it returns
     * true. Otherwise it returns false.
     */
    public static boolean isProperty(Field field) {
        return !Modifier.isTransient(field.getModifiers()) &&
                !Modifier.isStatic(field.getModifiers()) &&
                !field.isSynthetic() &&
                !void.class.equals(field.getType());
    }

    /**
     * Determines if the method is a property.
     *
     * @param method the method object
     * @return if the method is a property, it returns
     * true. Otherwise it returns false.
     */
    public static boolean isProperty(Method method) {
        return method.getParameterTypes().length == 0 &&
                !method.isSynthetic() &&
                !method.isBridge() &&
                !Modifier.isStatic(method.getModifiers()) &&
                (method.getName().startsWith("get") || method.getName().startsWith("is"));
    }

    /**
     * Determines if the annotated class is an instance of
     * the collection class.
     *
     * @param annotatedClass the annotated class
     * @return if the annotated class is an instance of
     * the collection class, it returns true. Otherwise it
     * returns false.
     */
    public static boolean isCollectionClass(Class annotatedClass) {
        return annotatedClass == Collection.class
                || annotatedClass == List.class
                || annotatedClass == Set.class
                || annotatedClass == Map.class
                || annotatedClass == SortedSet.class
                || annotatedClass == SortedMap.class;
    }

    /**
     * Decapitalizes the property name.
     *
     * @param propertyName the property name
     * @return Decapitalized property name
     */
    public static String decapitalize(String propertyName) {
        if (propertyName != null && propertyName.length() != 0) {
            return propertyName;
        }

        char[] chars = propertyName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

}
