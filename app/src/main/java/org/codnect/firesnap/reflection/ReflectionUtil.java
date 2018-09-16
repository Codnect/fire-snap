package org.codnect.firesnap.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

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
                !void.class.equals(method.getReturnType()) &&
                (method.getName().startsWith("get") || method.getName().startsWith("is"));
    }

    /**
     * Determines if the type is a simple type.
     *
     * @param type an instance of the type
     * @return if the type is a simple type, it returns
     * true. Otherwise, it returns false.
     */
    public static boolean isSimple(Type type) {

        if(type instanceof Class) {
            return !((Class) type).isArray() && !isCollectionClass((Class) type);
        }
        else if(type instanceof ParameterizedType) {
            return isSimple(((ParameterizedType) type).getRawType());
        }
        else if(type instanceof WildcardType) {
            Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
            for(Type lowerBound : lowerBounds) {
                if(!isSimple(lowerBound)) {
                    return false;
                }
            }
            Type[] upperBounds = ((WildcardType) type).getLowerBounds();
            for(Type upperBound : upperBounds) {
                if(!isSimple(upperBound)) {
                    return false;
                }
            }

            return true;
        }

        return false;

    }

    /**
     * Determines if the type is a array type.
     *
     * @param type an instance of the type
     * @return if the type is a array type, it returns
     * true. Otherwise, it returns false.
     */
    public static boolean isArray(Type type) {

        if(type instanceof Class) {
            return ((Class) type).isArray();
        } else if(type instanceof GenericArrayType) {
            return isSimple(((GenericArrayType) type).getGenericComponentType());
        }

        return false;
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
        return Collection.class.isAssignableFrom(annotatedClass)
                || Map.class.isAssignableFrom(annotatedClass);
    }

    /**
     * Get the collection class from type.
     *
     * @param type an instance of the type
     * @return the collection class from type.
     */
    public static Class getCollectionClass(Type type) {

        if(type instanceof Class) {
            if(isCollectionClass((Class) type)) {
                return (Class) type;
            }
        } else if(type instanceof ParameterizedType) {
            return getCollectionClass(((ParameterizedType) type).getRawType());
        } else if(type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if(upperBounds.length != 0){
                return getCollectionClass(upperBounds[0]);
            }
        }

        return null;
    }

    /**
     * Determines if the type is a collection type.
     *
     * @param type an instance of the type
     * @return if the type is a collection type, it returns
     * true. Otherwise, it returns false.
     */
    public static boolean isCollection(Type type) {
        if(getCollectionClass(type) != null) {
            return true;
        }
        return false;
    }

    /**
     * Decapitalizes the property name.
     *
     * @param propertyName the property name
     * @return Decapitalized property name
     */
    public static String decapitalize(String propertyName) {

        if (propertyName != null && propertyName.length() != 0) {
            char[] chars = propertyName.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }

        return propertyName;
    }

    /**
     * Determines if the specified type is a resolved type.
     *
     * @param type an instance of the type
     * @return if the type is a resolved type, it returns
     * true. Otherwise, it returns false.
     */
    public static boolean isResolved(Type type) {

        if(type instanceof Class) {
            return true;
        } else if(type instanceof GenericArrayType) {
            return isResolved(((GenericArrayType) type).getGenericComponentType());
        } else if(type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            for(Type actualTypeArgument : actualTypeArguments) {
                if(!isResolved(actualTypeArgument)) {
                    return false;
                }
            }
            return isResolved(((ParameterizedType) type).getRawType());
        } else if(type instanceof TypeVariable) {
            return false;
        } else if(type instanceof WildcardType) {
            Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
            for(Type lowerBound : lowerBounds) {
                if(!isResolved(lowerBound)) {
                    return false;
                }
            }

            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            for(Type upperBound : upperBounds) {
                if(!isResolved(upperBound)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }


}
