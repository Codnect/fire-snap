package org.codnect.firesnap.core;

import static junit.framework.Assert.*;

import org.codnect.firesnap.annotation.InheritanceStrategy;
import org.codnect.firesnap.binder.AnnotationBinder;
import org.codnect.firesnap.reflection.ReflectionManager;
import org.codnect.firesnap.reflection.XClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak Koken on 26.9.2018.
 *
 * @author Burak Koken
 */
public class AnnotationBinderTest {

    private MetadataContext metadataContext;
    private Map<XClass, InheritanceState> inheritanceStateMap;
    private ReflectionManager reflectionManager;

    @Before
    public void initTestObjects() {
        metadataContext = new MetadataContext(new MetadataCollector());
        reflectionManager = metadataContext.getReflectionManager();
    }

    @Test
    public void createInheritanceStatesTest() {
        /* initialize */
        List<XClass> classes = new ArrayList<>();
        classes.add(reflectionManager.getXClass(FooParent.class));
        classes.add(reflectionManager.getXClass(Foo.class));
        inheritanceStateMap = AnnotationBinder.createInheritanceStates(classes, metadataContext);
        /* mapped super class test */
        InheritanceState fooParentState = inheritanceStateMap.get(reflectionManager.getXClass(FooParent.class));
        assertFalse(fooParentState.hasParents());
        assertTrue(fooParentState.hasSiblings());
        assertTrue(fooParentState.isEmbeddableSuperclass());
        assertNull(fooParentState.getStrategy());
        /* model test */
        InheritanceState fooState = inheritanceStateMap.get(reflectionManager.getXClass(Foo.class));
        assertFalse(fooState.hasParents());
        assertFalse(fooState.hasSiblings());
        assertFalse(fooState.isEmbeddableSuperclass());
        assertEquals(InheritanceStrategy.SINGLE_NODE, fooState.getStrategy());
    }

}
