package org.codnect.firesnap.reflection;

import org.codnect.firesnap.binder.AnnotationBinder;
import org.codnect.firesnap.model.Foo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak Koken on 10.9.2018.
 *
 * @author Burak Koken
 */
public class ApproximateTypeBinderTestModel<E,T> {

    public E getTypeVariableProperty() {
        return null;
    }

    public <E extends Object> E getTypeVariableWithExtendsProperty() {
        return null;
    }

    public List<E> getParameterizedTypeWithTypeVariable() {
        return null;
    }

    public List<Integer> getParameterizedTypeProperty() {
        return new ArrayList<>();
    }

    public List<?> getUnboundedWildcardTypeProperty() {
        return null;
    }

    public List<? extends Integer> getWildcardTypeProperty() {
        return null;
    }

    public List<String>[] getGenericArrayTypeProperty() {
        return new ArrayList[0];
    }

}
