package org.codnect.firesnap.reflection;

import junit.framework.Assert;

import org.codnect.firesnap.model.ReflectionTestModel;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        Assert.assertEquals(true, ReflectionUtil.isProperty(field));
    }

    @Test
    public void testIsPropertyForMethod() throws NoSuchMethodException {
        Method method = testModelClass.getDeclaredMethod("setProductName", String.class);
        Assert.assertEquals(false, ReflectionUtil.isProperty(method));
        method = testModelClass.getDeclaredMethod("getProductName", null);
        Assert.assertEquals(true, ReflectionUtil.isProperty(method));
    }

    @Test
    public void testIsCollectionClass() {
        Assert.assertEquals(false, ReflectionUtil.isCollectionClass(ReflectionTestModel.class));
        Assert.assertEquals(true, ReflectionUtil.isCollectionClass(List.class));
    }

    @Test
    public void testDecapitalize() {
        Assert.assertEquals("productName", ReflectionUtil.decapitalize("ProductName"));
    }

}
