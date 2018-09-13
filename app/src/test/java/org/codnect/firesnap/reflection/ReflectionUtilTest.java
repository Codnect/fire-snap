package org.codnect.firesnap.reflection;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Burak Koken on 21.5.2018.
 *
 * @author Burak Koken
 */
public class ReflectionUtilTest {

    private Class testModelClass;

    @Before
    public void initTestObjects() {
        testModelClass = ReflectionTestModel.class;
    }

    @Test
    public void testIsPropertyForField() throws NoSuchFieldException {
        Field field = testModelClass.getDeclaredField("productName");
        assertTrue(ReflectionUtil.isProperty(field));
    }

    @Test
    public void testIsPropertyForMethod() throws NoSuchMethodException {
        Method method = testModelClass.getDeclaredMethod("setProductName", String.class);
        assertFalse(ReflectionUtil.isProperty(method));
        method = testModelClass.getDeclaredMethod("getProductName", null);
        assertTrue(ReflectionUtil.isProperty(method));
    }

    @Test
    public void testIsCollectionClass() {
        assertFalse(ReflectionUtil.isCollectionClass(ReflectionTestModel.class));
        assertTrue(ReflectionUtil.isCollectionClass(List.class));
    }

    @Test
    public void testDecapitalize() {
        assertEquals("productName", ReflectionUtil.decapitalize("ProductName"));
    }

    @Test
    public void testIsSimple() throws NoSuchMethodException {
        Type classType = getMethodTypeFromReflectionTypeTestModel("getObjectProperty");
        assertTrue(ReflectionUtil.isSimple(classType));

        Type arrayType = getMethodTypeFromReflectionTypeTestModel("getArrayProperty");
        assertFalse(ReflectionUtil.isSimple(arrayType));

        Type primitiveType = getMethodTypeFromReflectionTypeTestModel("getPrimitiveProperty");
        assertTrue(ReflectionUtil.isSimple(primitiveType));

        Type parameterizedType = getMethodTypeFromReflectionTypeTestModel("getCollectionProperty");
        assertFalse(ReflectionUtil.isSimple(parameterizedType));

        Type genericArrayType = getMethodTypeFromReflectionTypeTestModel("getGenericArrayTypeProperty");
        assertFalse(ReflectionUtil.isSimple(genericArrayType));

        Type wilcardType = getMethodTypeFromReflectionTypeTestModel("getWilcardTypeProperty");
        assertFalse(ReflectionUtil.isSimple(wilcardType));

        Type typeVariable = getMethodTypeFromReflectionTypeTestModel("getTypeVariableProperty");
        assertFalse(ReflectionUtil.isSimple(typeVariable));
    }

    public Type getMethodTypeFromReflectionTypeTestModel(String methodName) throws NoSuchMethodException {
        return ReflectionTypeTestModel.class.getMethod(methodName, new Class[0])
                .getGenericReturnType();
    }
}
