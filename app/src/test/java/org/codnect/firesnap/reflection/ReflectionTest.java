package org.codnect.firesnap.reflection;

import junit.framework.Assert;

import org.codnect.firesnap.model.ReflectionTestModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Burak Koken on 29.5.2018.
 *
 * @author Burak Koken
 */
public class ReflectionTest {

    private ReflectionManager reflectionManager;
    private XClass xClass;

    @Before
    public void initTestObjects() {
        reflectionManager = new ReflectionManager();
        xClass = reflectionManager.getXClass(ReflectionTestModel.class);
    }

    @Test
    public void testXClassGetDeclaredFields() {
        List<XField> xFieldList = xClass.getDeclaredFields();
        Assert.assertEquals(5, xFieldList.size());
    }

    @Test
    public void testXClassGetDeclaredMethods() {
        List<XMethod> xMethodList = xClass.getDeclaredMethods();
        Assert.assertEquals(10, xMethodList.size());
    }

    @Test
    public void testXClassGetDeclaredFieldProperties() {
        List<XProperty> xFieldPropertyList = xClass.getDeclaredFieldProperties();
        Assert.assertEquals(5, xFieldPropertyList.size());
    }

    @Test
    public void testXClassGetDeclaredMethodProperties() {
        List<XProperty> xMethodPropertyList = xClass.getDeclaredMethodProperties();
        Assert.assertEquals(4, xMethodPropertyList.size());
    }

}
