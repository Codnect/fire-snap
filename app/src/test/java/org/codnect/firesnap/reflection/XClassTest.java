package org.codnect.firesnap.reflection;

import org.codnect.firesnap.reflection.binder.TypeBinder;
import org.codnect.firesnap.reflection.binder.TypeBinderFactory;
import org.codnect.firesnap.scan.PackageUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Burak Koken on 13.9.2018.
 *
 * @author Burak Koken
 */
public class XClassTest {

    private ReflectionManager reflectionManager;
    private XClass xClass;
    private XClass xClassSuper;

    @Before
    public void initTestObjects() {
        reflectionManager = new ReflectionManager();
        xClass = reflectionManager.getXClass(Foo.class);
        xClassSuper = reflectionManager.getXClass(FooSuper.class);
    }

    @Test
    public void test() {
        xClassSuper.getDeclaredMethods();
        System.out.println();
    }
}
