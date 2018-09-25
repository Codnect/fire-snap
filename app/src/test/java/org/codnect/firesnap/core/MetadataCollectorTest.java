package org.codnect.firesnap.core;

import org.codnect.firesnap.reflection.ReflectionManager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by Burak Koken on 25.9.2018.
 *
 * @author Burak Koken
 */
public class MetadataCollectorTest {

    private static ReflectionManager reflectionManager;
    private static MetadataCollector collector;

    @Before
    public void initTestObjects() {
        reflectionManager = new ReflectionManager();
        collector = new MetadataCollector();
    }

    @Test
    public void getClassTypeTest() {
        assertEquals(collector.getClassType(reflectionManager.getXClass(Foo.class)), AnnotatedClassType.MODEL);
        assertEquals(collector.getClassType(reflectionManager.getXClass(FooParent.class)), AnnotatedClassType.EMBEDDABLE_SUPERCLASS);
    }

}
